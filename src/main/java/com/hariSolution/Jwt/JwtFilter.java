package com.hariSolution.Jwt;

import com.hariSolution.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;  // Utility for handling JWT operations (extract username, validate token)
    private final CustomUserDetailsService customUserDetailsService;  // Service for loading user details from the database

    // Constructor that initializes dependencies through constructor injection
    public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    // Method that performs the filtering logic for incoming requests
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Extract the Authorization header from the request
        String authorizationHeader = request.getHeader("Authorization");

        String token = null;
        String username = null;

        // Check if the token exists and starts with the "Bearer " prefix
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);  // Extract the token (without the "Bearer " prefix)
            username = jwtUtil.extractUserName(token);  // Extract the username (subject) from the token
        }

        // Validate the token if the username is not null and no authentication exists in the security context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details using the CustomUserDetailsService
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // Validate the token using the utility method
            if (jwtUtil.isTokenValid(token, userDetails)) {
                // If the token is valid, create a new authentication token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());  // Authentication token with authorities (roles)

                // Set request details (like IP address) to the authentication token
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication context with the newly created authentication token
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue the filter chain by passing the request and response to the next filter in the chain
        filterChain.doFilter(request, response);
    }
}
