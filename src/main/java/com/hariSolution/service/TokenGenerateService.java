package com.hariSolution.service;

import com.hariSolution.Jwt.JwtUtil; // Utility class for generating JWT tokens
import com.hariSolution.model.UserInfo; // Model representing user information (e.g., username and password)
import lombok.RequiredArgsConstructor; // Lombok annotation for constructor injection
import org.springframework.cache.annotation.Cacheable; // Annotation for caching the result
import org.springframework.security.authentication.AuthenticationManager; // Interface for authenticating users
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; // Class representing the authentication token
import org.springframework.stereotype.Service; // Marks this class as a Spring service

@Service // Marks this class as a Spring service
@RequiredArgsConstructor // Lombok generates the constructor for the dependencies
public class TokenGenerateService {

    private final AuthenticationManager authenticationManager; // Used to authenticate the user
    private final JwtUtil jwtUtil; // Utility class for JWT generation

    // Method to generate a JWT token for the user
    @Cacheable(value = "shortLivedCache", key = "#loginRequest.username", unless = "#result == null", cacheManager = "cacheManager")
    public String generateToken(UserInfo loginRequest) {
        // Authenticate the user by using the provided username and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),  // Username
                        loginRequest.getPassword()   // Password
                )
        );

        // If authentication is successful, generate and return the token
        return jwtUtil.generateToken(loginRequest.getUsername());
    }
}
