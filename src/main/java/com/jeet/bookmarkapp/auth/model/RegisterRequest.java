package com.jeet.bookmarkapp.auth.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
    @NotBlank
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    private String username;
    //TODO: Will Add extra fields later
}
