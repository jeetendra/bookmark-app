package com.jeet.bookmarkapp.auth.controller;

import com.jeet.bookmarkapp.auth.JwtService;
import com.jeet.bookmarkapp.auth.model.LoginRequest;
import com.jeet.bookmarkapp.auth.model.LoginResponse;
import com.jeet.bookmarkapp.auth.model.RegisterRequest;
import com.jeet.bookmarkapp.entity.User;
import com.jeet.bookmarkapp.enums.Role;
import com.jeet.bookmarkapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.webauthn.api.AuthenticatorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> doLogin(@Valid @RequestBody LoginRequest user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> doRegister(@Valid @RequestBody RegisterRequest user) {
        User newUser = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .roles(Set.of(Role.ROLE_USER))
                .build();
        userService.save(newUser, null);

        return ResponseEntity.status(HttpStatus.OK).body("User registered successfully");
    }
}
