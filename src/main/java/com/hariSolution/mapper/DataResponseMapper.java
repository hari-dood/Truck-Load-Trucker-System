package com.hariSolution.mapper;

import com.hariSolution.model.DataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component  // Marks this class as a Spring component, enabling it to be automatically detected by Spring's component scanning
public class DataResponseMapper {

    // Method to create a structured DataResponse object containing the status and data
    public DataResponse createResponse(Map<String, Object> data, String status, int statusCode, HttpStatus statusMessage,
                                       String requestId) {

        // Create a new DataResponse object to hold the response data
        DataResponse response = new DataResponse();

        // Create a map to hold the status details of the response
        Map<String, Object> statusDetails = new HashMap<>();

        // Populate the statusDetails map with relevant information
        statusDetails.put("status", status);                    // Status of the response (e.g., "success" or "error")
        statusDetails.put("statusCode", statusCode);            // HTTP status code (e.g., 200, 400, etc.)
        statusDetails.put("status_message", statusMessage);     // Detailed status message (e.g., "OK", "Bad Request")
        statusDetails.put("requestId", requestId);              // Unique request ID for tracking the request
        statusDetails.put("_server_timestamp", Instant.now().toString());  // Timestamp of when the response was generated

        // Set the status details map in the response object
        response.setStatus_details(statusDetails);

        // Set the data field in the response object
        response.setData(data);  // The actual response data (can be any data passed to the method)

        // Return the fully populated response object
        return response;
    }

}
