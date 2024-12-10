package com.jeet.bookmarkapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Name is required")
    private String name;
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    private String username;
    @Email(message = "Email")
    private String email;
    @Size(min = 5, max = 20, message = "Password must be between 5 and 20 characters")
    private String password;
}
