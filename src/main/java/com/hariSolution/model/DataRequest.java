package com.hariSolution.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hariSolution.DTOs.TripDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data  // Generates getters, setters, toString, equals, and hashCode methods automatically
@NoArgsConstructor  // Generates a no-args constructor
@AllArgsConstructor  // Generates a constructor with parameters for all fields
@Component  // Marks this class as a Spring-managed bean
@JsonSerialize  // Ensures this class can be serialized by Jackson (useful for JSON serialization)
public class DataRequest implements Serializable {
    private static final long serialVersionUID = 1L;  // Serial version UID for serialization compatibility

    private TripDetailsDTO data;  // Data field which contains TripDetailsDTO object

    private String _request_id;  // Unique identifier for the request
    private String _client_ts;  // Client timestamp when the request was created
    private String _client_type;  // Type of client (e.g., web, mobile)
}
