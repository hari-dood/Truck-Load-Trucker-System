package com.hariSolution.mapper;

import com.hariSolution.DTOs.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component  // Marks this class as a Spring component to be registered as a bean
public class AuthResponseMapper {

    // Method to create a response object with status details and message
    public AuthResponse createResponse(String message, String details, HttpStatus status) {
        // Create a new AuthResponse object to hold the response data
        AuthResponse response = new AuthResponse();

        // Create a map to store status-related information
        Map<String, Object> statusDetails = new HashMap<>();

        // Add timestamp, status name, and status code to the status details
        statusDetails.put("timestamp", Instant.now().toString());  // Add current timestamp
        statusDetails.put("status", status.name());  // Status name (e.g., OK, BAD_REQUEST)
        statusDetails.put("statusCode", status.value());  // Numeric status code (e.g., 200, 400)

        // Create another map to store the message details
        Map<String, String> messageDetails = new HashMap<>();
        messageDetails.put("message", message);  // Add the message to the map
        messageDetails.put("details", details);  // Add the details to the map

        // Add message details to the status details map
        statusDetails.put("responseDetails", messageDetails);

        // Set the status details map in the AuthResponse object
        response.setStatus_details(statusDetails);

        // Return the fully populated response object
        return response;
    }
}
