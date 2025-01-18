package com.hariSolution.DTOs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data  // Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates an all-argument constructor
@JsonSerialize // Ensures the object can be serialized to JSON
public class RegisterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "Username cannot be empty")  // Ensures the username is not empty
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,15}$", message = "Username must be between 3 and 15 characters and can contain only alphanumeric characters, underscores, and hyphens") // Validates username format
    private String username;

    @NotEmpty(message = "Password cannot be empty")  // Ensures the password is not empty
    @Size(min = 8, message = "Password must be at least 8 characters long")  // Ensures password is at least 8 characters long
    @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")  // Ensures password contains at least one uppercase letter
    @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter")  // Ensures password contains at least one lowercase letter
    @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one digit")  // Ensures password contains at least one digit
    @Pattern(regexp = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*", message = "Password must contain at least one special character")  // Ensures password contains at least one special character
    private String password;

    @NotEmpty(message = "Email cannot be empty")  // Ensures the email is not empty
    @Email(message = "Email should be valid")  // Ensures the email is valid
    private String email;

    @NotEmpty(message = "Roles cannot be empty")  // Ensures roles are not empty
    private List<String> roles;  // List of roles the user will have, such as "USER", "ADMIN", etc.
}
