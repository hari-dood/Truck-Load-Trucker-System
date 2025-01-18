package com.hariSolution.DTOs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data  // Lombok annotation that generates getters, setters, toString, equals, and hashCode methods
@RequiredArgsConstructor // Lombok annotation that generates a constructor with required fields
@JsonSerialize // Ensures the object can be serialized to JSON format
public class RoleInfoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Role name cannot be blank")  // Ensures that the role name is not blank
    @Size(min = 3, max = 50, message = "Role name must be between 3 and 50 characters")  // Validates that the role name is between 3 and 50 characters
    private String name;

    @NotBlank(message = "Role description cannot be blank")  // Ensures that the role description is not blank
    @Size(min = 5, max = 200, message = "Role description must be between 5 and 200 characters")  // Validates that the role description is between 5 and 200 characters
    private String roleDescription;
}
