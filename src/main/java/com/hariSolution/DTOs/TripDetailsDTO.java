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

@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
@JsonSerialize
public class TripDetailsDTO implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;


        @NotBlank(message = "Driver Name is required")
        @Pattern(regexp = "^[\\p{L} ]+$", message = "Driver Name must only contain alphabetic characters and spaces")
        @Size(max = 100, message = "Driver Name cannot exceed 100 characters")
        private String driverName ;

        @NotBlank(message = "Month is required")
        private String month;

        @NotNull(message = "Loading Date is required")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        private LocalDate loadingDate;

        @NotNull(message = "Trip Start Date is required")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        private LocalDate tripStartDate;

        @NotNull(message = "Trip End Date is required")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        private LocalDate tripEndDate;

        @NotBlank(message = "Payment Status is required")
        @Pattern(regexp = "^(Pending|Partially Paid|Paid)$", message = "Payment Status must be 'Pending', 'Partially Paid', or 'Paid'")
        private String paymentStatus;

        @NotBlank(message = "Loading Point is required")
        @Size(max = 100, message = "Loading Point cannot exceed 100 characters")
        private String loadingPoint;

        @NotBlank(message = "Delivery Point is required")
        @Size(max = 100, message = "Delivery Point cannot exceed 100 characters")
        private String deliveryPoint;

        @NotBlank(message = "Load Quantity is required")
        @Pattern(regexp = "^\\d+(\\.\\d+)?\\s*(kg|tons|kgm|lbs)$", message = "Load Quantity must be a valid weight (e.g., '100 kg', '10 tons')")
        private String loadQuantity;

        @NotBlank(message = "Goods Description is required")
        @Size(max = 100, message = "Goods Description cannot exceed 100 characters")
        @Pattern(regexp = "^[a-zA-Z0-9\\s\\-.,()]+$", message = "Goods Description must contain only letters, numbers, spaces, and valid symbols (-, ., ,)")
        private String goodsDescription;

        @NotNull(message = "Load Amount cannot be null")
        @Positive(message = "Load Amount must be a positive value")
        private Integer loadAmount;

        @NotNull(message = "Load Advance cannot be null")
        @PositiveOrZero(message = "Load Advance must be zero or a positive value")
        private Integer loadAdvance;

        @NotNull(message = "Weight Bridge expense cannot be null")
        @PositiveOrZero(message = "Weight Bridge expense must be zero or a positive value")
        private Integer weightBridge;

        @NotNull(message = "Broker Amount cannot be null")
        @PositiveOrZero(message = "Broker Amount must be zero or a positive value")
        private Integer brokerAmount;

        @NotNull(message = "Up Amount cannot be null")
        @PositiveOrZero(message = "Up Amount must be zero or a positive value")
        private Integer upAmount;

        @NotNull(message = "Down Amount cannot be null")
        @PositiveOrZero(message = "Down Amount must be zero or a positive value")
        private Integer downAmount;

        @NotNull(message = "Toll Amount cannot be null")
        @PositiveOrZero(message = "Toll Amount must be zero or a positive value")
        private Integer tollAmount;

        @NotNull(message = "Diesel Expenses cannot be null")
        @PositiveOrZero(message = "Diesel Expenses must be zero or a positive value")
        private Integer dieselExpenses;

        @NotNull(message = "RTO Amount cannot be null")
        @PositiveOrZero(message = "RTO Amount must be zero or a positive value")
        private Integer rtoAmount;

        @NotNull(message = "PC Amount cannot be null")
        @PositiveOrZero(message = "PC Amount must be zero or a positive value")
        private Integer pcAmount;

        @NotNull(message = "Other Expenses cannot be null")
        @PositiveOrZero(message = "Other Expenses must be zero or a positive value")
        private Integer othersExpenses;

        @NotNull(message = "Total Expenses cannot be null")
        @PositiveOrZero(message = "Total Expenses must be zero or a positive value")
        private Integer totalExpenses;

        @Pattern(regexp = "[A-Za-z0-9]+", message = "Client request ID must be alphanumeric")
        private String clientReqId;
}
