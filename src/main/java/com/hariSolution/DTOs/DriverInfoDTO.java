package com.hariSolution.DTOs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data  // Lombok annotation to automatically generate getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor  // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor  // Lombok annotation to generate a constructor with parameters for all fields
@JsonSerialize  // Jackson annotation to indicate that the object can be serialized into JSON
public class DriverInfoDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;  // Serialization version ID for compatibility

    @NotBlank(message = "First Name is required")  // Ensures the field is not empty
    @Size(max = 50, message = "First Name cannot exceed 50 characters")  // Limits the length to 50 characters
    @Pattern(regexp = "^[\\p{L} ]+$", message = "First Name must only contain alphabetic characters and spaces")  // Restricts to alphabetic characters and spaces
    private String firstName;

    @NotBlank(message = "Full Name is required")  // Ensures the field is not empty
    @Size(max = 100, message = "Full Name cannot exceed 100 characters")  // Limits the length to 100 characters
    @Pattern(regexp = "^[\\p{L} ]+$", message = "Full Name must only contain alphabetic characters and spaces")  // Restricts to alphabetic characters and spaces
    private String fullName;

    @NotBlank(message = "Contact Number is required")  // Ensures the field is not empty
    @Pattern(regexp = "^\\d{10,15}$", message = "Contact Number must be between 10 to 15 digits")  // Validates that the number contains between 10 and 15 digits
    private String contactNumber;

    @NotBlank(message = "Email is required")  // Ensures the field is not empty
    @Email(message = "Invalid email format")  // Validates email format
    @Size(max = 50, message = "Email cannot exceed 50 characters")  // Limits the length to 50 characters
    private String email;

    @NotBlank(message = "Experience is required")  // Ensures the field is not empty
    private String experience;

    @NotBlank(message = "Licence Number is required")  // Ensures the field is not empty
    @Size(max = 20, message = "Licence Number cannot exceed 20 characters")  // Limits the length to 20 characters
    private String licenceNo;

    @NotBlank(message = "Aadhaar Card Number is required")  // Ensures the field is not empty
    @Pattern(regexp = "^\\d{12,16}$", message = "Aadhaar Card Number must be between 12 to 16 digits")  // Validates the Aadhaar number length
    private String aadhaarNo;

    @NotNull(message = "Address is required")  // Ensures the field is not null
    @Size(max = 500, message = "Address cannot exceed 500 characters")  // Limits the length to 500 characters
    private String address;
}
