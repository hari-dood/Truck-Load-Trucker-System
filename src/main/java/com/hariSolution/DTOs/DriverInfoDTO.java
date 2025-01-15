package com.hariSolution.DTOs;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class DriverInfoDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "First Name is required")
    @Size(max = 50, message = "First Name cannot exceed 50 characters")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "firstName Name must only contain alphabetic characters and spaces")
    private String firstName;

    @NotBlank(message = "Full Name is required")
    @Size(max = 100, message = "Full Name cannot exceed 100 characters")
    @Pattern(regexp = "^[\\p{L} ]+$", message = "fullName Name must only contain alphabetic characters and spaces")
    private String fullName;

    @NotBlank(message = "Contact Number is required")
    @Pattern(regexp = "^\\d{10,15}$", message = "Contact Number must be between 10 to 15 digits")
    private String contactNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 50, message = "Email cannot exceed 50 characters")
    private String email;

    @NotBlank(message = "Experience is required")
    private String experience;

    @NotBlank(message = "Licence Number is required")
    @Size(max = 20, message = "Licence Number cannot exceed 20 characters")
    private String licenceNo;

    @NotBlank(message = "Aadhaar Card Number is required")
    @Pattern(regexp = "^\\d{12,16}$", message = "Aadhaar Card Number must be between 12 to 16 digits")
    private String aadhaarNo;

    @NotNull(message = "Address is required")
    @Size(max = 500, message = "Address cannot exceed 500 characters")
    private String address;

}
