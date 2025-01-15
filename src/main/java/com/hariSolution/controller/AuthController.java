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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerateService tokenService;
    private final AuthResponseMapper authResponseService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {

            AuthResponse response = authResponseService.createResponse(
                    "Username is already taken", // Error message
                    "Please try a different username.", // Additional details
                    HttpStatus.CONFLICT
            );
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        UserInfo newUser = new UserInfo();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setEmail(registerRequest.getEmail());

        List<RoleInfo> roles = new ArrayList<>();

        registerRequest.getRoles().forEach(roleName->{
            RoleInfo role=roleRepository.findByName(roleName).
                    orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(role);
        });
       /* for (String roleName : registerRequest.getRoles()) {
            RoleInfo role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(role);
        }*/
        newUser.setRoles(roles);

        userRepository.save(newUser);
        AuthResponse response = authResponseService.createResponse(
                "User registered successfully",
                "The user has been added to the system and is now active.",
                HttpStatus.OK
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserInfo loginRequest) {
        try {
            String token=tokenService.generateToken(loginRequest);
            return ResponseEntity.ok(token);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login");
        }
    }


}
