package com.hariSolution.DTOs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data  // Lombok annotation to automatically generate getters, setters, toString, equals, and hashCode methods
@JsonSerialize  // Jackson annotation to indicate that the object can be serialized into JSON
public class LoginRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;  // Serialization version ID for compatibility

    @NotEmpty(message = "Username cannot be empty")  // Ensures that the username is not empty
    private String username;

    @NotEmpty(message = "Password cannot be empty")  // Ensures that the password is not empty
    @Size(min = 8, message = "Password must be at least 8 characters long")  // Validates that the password is at least 8 characters long
    private String password;

}
