package com.hariSolution.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AuthResponse {

    private Map<String,Object> status_details;
}
