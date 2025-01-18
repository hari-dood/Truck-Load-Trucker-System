package com.hariSolution.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data  // Lombok annotation to automatically generate getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor  // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor  // Lombok annotation to generate a constructor with all arguments
@Component  // Marks this class as a Spring-managed bean (component) so it can be injected where needed
public class AuthResponse {

    private Map<String,Object> status_details;  // A map to hold status details related to authentication or authorization
}
