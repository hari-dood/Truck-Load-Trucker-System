package com.hariSolution.controller;

import com.hariSolution.DTOs.AuthResponse;
import com.hariSolution.DTOs.RegisterRequest;
import com.hariSolution.mapper.AuthResponseMapper;
import com.hariSolution.model.RoleInfo;
import com.hariSolution.model.UserInfo;
import com.hariSolution.repository.RoleRepository;
import com.hariSolution.repository.UserRepository;
import com.hariSolution.service.TokenGenerateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController  // Marks this class as a Spring REST controller
@RequestMapping("/auth")  // Specifies the base URL for all request mappings in this class
@RequiredArgsConstructor  // Automatically generates a constructor for required final fields
public class AuthController {

    private final UserRepository userRepository;  // Repository to interact with User data in the database
    private final RoleRepository roleRepository;  // Repository to interact with Role data
    private final PasswordEncoder passwordEncoder;  // Password encoder for securely hashing passwords
    private final TokenGenerateService tokenService;  // Service for generating JWT tokens
    private final AuthResponseMapper authResponseService;  // Service for mapping responses

    // Endpoint for user registration
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        // Check if the username already exists in the database
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            // If username is taken, return a conflict response
            AuthResponse response = authResponseService.createResponse(
                    "Username is already taken", // Error message
                    "Please try a different username.", // Additional details
                    HttpStatus.CONFLICT
            );
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);  // Return HTTP 409
        }

        // Create a new user object
        UserInfo newUser = new UserInfo();
        newUser.setUsername(registerRequest.getUsername());  // Set username
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));  // Set encoded password
        newUser.setEmail(registerRequest.getEmail());  // Set email

        // Set roles for the new user based on the request
        List<RoleInfo> roles = new ArrayList<>();
        // Iterate over the roles in the request and fetch from the repository
        registerRequest.getRoles().forEach(roleName -> {
            RoleInfo role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));  // Handle role not found
            roles.add(role);  // Add the found role to the list
        });

        newUser.setRoles(roles);  // Set the roles for the new user

        // Save the new user to the database
        userRepository.save(newUser);

        // Create a response indicating successful registration
        AuthResponse response = authResponseService.createResponse(
                "User registered successfully",  // Success message
                "The user has been added to the system and is now active.", // Additional details
                HttpStatus.OK  // HTTP status 200
        );

        return ResponseEntity.ok(response);  // Return the response with HTTP 200
    }

    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserInfo loginRequest) {
        try {
            // Generate a token for the user on successful login
            String token = tokenService.generateToken(loginRequest);
            return ResponseEntity.ok(token);  // Return the generated token in the response

        } catch (BadCredentialsException e) {
            // Handle invalid credentials (wrong username or password)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            // Catch any other exceptions and return an internal server error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login");
        }
    }
}
