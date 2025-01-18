package com.hariSolution.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor  // Generates a no-argument constructor
@AllArgsConstructor  // Generates an all-argument constructor
@Data  // Generates getters, setters, toString(), equals(), and hashCode() methods
@Component  // Marks the class as a Spring component for dependency injection
@JsonSerialize  // Ensures the object can be serialized into JSON
public class TripDetailsDTO implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L; // Serial version UID for serialization

        // Driver's name field
        @NotBlank(message = "Driver Name is required")  // Ensures the field is not null or blank
        @Pattern(regexp = "^[\\p{L} ]+$", message = "Driver Name must only contain alphabetic characters and spaces")  // Ensures the name contains only letters and spaces
        @Size(max = 100, message = "Driver Name cannot exceed 100 characters")  // Ensures the name does not exceed 100 characters
        private String driverName;

        // Month of the trip
        @NotBlank(message = "Month is required")  // Ensures month is not empty
        private String month;

        // Loading date of the trip
        @NotNull(message = "Loading Date is required")  // Ensures date is not null
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")  // Specifies date format for serialization/deserialization
        private LocalDate loadingDate;

        // Trip start date
        @NotNull(message = "Trip Start Date is required")  // Ensures date is not null
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")  // Specifies date format for serialization/deserialization
        private LocalDate tripStartDate;

        // Trip end date
        @NotNull(message = "Trip End Date is required")  // Ensures date is not null
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")  // Specifies date format for serialization/deserialization
        private LocalDate tripEndDate;

        // Payment status of the trip
        @NotBlank(message = "Payment Status is required")  // Ensures payment status is not empty
        @Pattern(regexp = "^(Pending|Partially Paid|Paid)$", message = "Payment Status must be 'Pending', 'Partially Paid', or 'Paid'")  // Validates payment status values
        private String paymentStatus;

        // Loading point where goods are picked up
        @NotBlank(message = "Loading Point is required")  // Ensures loading point is not empty
        @Size(max = 100, message = "Loading Point cannot exceed 100 characters")  // Ensures loading point does not exceed 100 characters
        private String loadingPoint;

        // Delivery point where goods are delivered
        @NotBlank(message = "Delivery Point is required")  // Ensures delivery point is not empty
        @Size(max = 100, message = "Delivery Point cannot exceed 100 characters")  // Ensures delivery point does not exceed 100 characters
        private String deliveryPoint;

        // Load quantity to be transported (e.g., in kg, tons, etc.)
        @NotBlank(message = "Load Quantity is required")  // Ensures load quantity is not empty
        @Pattern(regexp = "^\\d+(\\.\\d+)?\\s*(kg|tons|kgm|lbs)$", message = "Load Quantity must be a valid weight (e.g., '100 kg', '10 tons')")  // Validates the format of the load quantity
        private String loadQuantity;

        // Description of the goods being transported
        @NotBlank(message = "Goods Description is required")  // Ensures goods description is not empty
        @Size(max = 100, message = "Goods Description cannot exceed 100 characters")  // Ensures goods description does not exceed 100 characters
        @Pattern(regexp = "^[a-zA-Z0-9\\s\\-.,()]+$", message = "Goods Description must contain only letters, numbers, spaces, and valid symbols (-, ., ,)")  // Validates goods description format
        private String goodsDescription;

        // Load amount for the trip (must be a positive integer)
        @NotNull(message = "Load Amount cannot be null")  // Ensures load amount is not null
        @Positive(message = "Load Amount must be a positive value")  // Ensures load amount is a positive number
        private Integer loadAmount;

        // Load advance paid for the trip (must be zero or positive)
        @NotNull(message = "Load Advance cannot be null")  // Ensures load advance is not null
        @PositiveOrZero(message = "Load Advance must be zero or a positive value")  // Ensures load advance is zero or positive
        private Integer loadAdvance;

        // Weight Bridge expense (must be zero or positive)
        @NotNull(message = "Weight Bridge expense cannot be null")  // Ensures weight bridge expense is not null
        @PositiveOrZero(message = "Weight Bridge expense must be zero or a positive value")  // Ensures expense is zero or positive
        private Integer weightBridge;

        // Broker amount (must be zero or positive)
        @NotNull(message = "Broker Amount cannot be null")  // Ensures broker amount is not null
        @PositiveOrZero(message = "Broker Amount must be zero or a positive value")  // Ensures broker amount is zero or positive
        private Integer brokerAmount;

        // Amount for up expenses (must be zero or positive)
        @NotNull(message = "Up Amount cannot be null")  // Ensures up amount is not null
        @PositiveOrZero(message = "Up Amount must be zero or a positive value")  // Ensures up amount is zero or positive
        private Integer upAmount;

        // Amount for down expenses (must be zero or positive)
        @NotNull(message = "Down Amount cannot be null")  // Ensures down amount is not null
        @PositiveOrZero(message = "Down Amount must be zero or a positive value")  // Ensures down amount is zero or positive
        private Integer downAmount;

        // Toll amount (must be zero or positive)
        @NotNull(message = "Toll Amount cannot be null")  // Ensures toll amount is not null
        @PositiveOrZero(message = "Toll Amount must be zero or a positive value")  // Ensures toll amount is zero or positive
        private Integer tollAmount;

        // Diesel expenses for the trip (must be zero or positive)
        @NotNull(message = "Diesel Expenses cannot be null")  // Ensures diesel expenses is not null
        @PositiveOrZero(message = "Diesel Expenses must be zero or a positive value")  // Ensures diesel expenses is zero or positive
        private Integer dieselExpenses;

        // RTO amount (must be zero or positive)
        @NotNull(message = "RTO Amount cannot be null")  // Ensures RTO amount is not null
        @PositiveOrZero(message = "RTO Amount must be zero or a positive value")  // Ensures RTO amount is zero or positive
        private Integer rtoAmount;

        // PC amount (must be zero or positive)
        @NotNull(message = "PC Amount cannot be null")  // Ensures PC amount is not null
        @PositiveOrZero(message = "PC Amount must be zero or a positive value")  // Ensures PC amount is zero or positive
        private Integer pcAmount;

        // Other expenses for the trip (must be zero or positive)
        @NotNull(message = "Other Expenses cannot be null")  // Ensures other expenses is not null
        @PositiveOrZero(message = "Other Expenses must be zero or a positive value")  // Ensures other expenses is zero or positive
        private Integer othersExpenses;

        // Total expenses for the trip (must be zero or positive)
        @NotNull(message = "Total Expenses cannot be null")  // Ensures total expenses is not null
        @PositiveOrZero(message = "Total Expenses must be zero or a positive value")  // Ensures total expenses is zero or positive
        private Integer totalExpenses;

        // Client request ID (must be alphanumeric)
        @Pattern(regexp = "[A-Za-z0-9]+", message = "Client request ID must be alphanumeric")  // Ensures the client request ID is alphanumeric
        private String clientReqId;
}
