package com.hariSolution.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

@Data  // Automatically generates getters, setters, toString(), equals(), and hashCode() methods
@NoArgsConstructor  // Generates a no-arguments constructor
@AllArgsConstructor  // Generates a constructor with arguments for all fields
@Component  // Marks this class as a Spring-managed bean
public class DataResponse implements Serializable {
    private static final long serialVersionUID = 1L;  // Serial version UID for serialization compatibility

    private Map<String,Object> data;  // Contains the actual response data
    private Map<String,Object> status_details;  // Contains status-related details like status code, message, etc.
}
