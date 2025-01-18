package com.hariSolution.Jwt;

import com.hariSolution.model.RoleInfo;
import com.hariSolution.model.UserInfo;
import com.hariSolution.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class JwtUtil {

    // Secret key used to sign the JWT token
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // Token expiration time set to 24 hours (in milliseconds)
    private static final int JWT_EXPIRATION_MS = 86400000; // 24 hours in milliseconds

    private final UserRepository userRepository; // Repository for fetching user data

    // Constructor initializing the user repository
    public JwtUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Method to generate a JWT token using the username
    public String generateToken(String username) {
        // Fetch user information based on the username
        Optional<UserInfo> userInfo = userRepository.findByUsername(username);

        // If user is not found, throw an exception
        if (userInfo.isEmpty()) {
            throw new IllegalArgumentException("User not found: " + username);
        }

        // Extract the roles associated with the user
        List<RoleInfo> roles = userInfo.get().getRoles();

        // Generate a JWT token with the subject (username), roles, issued time, expiration time, and the secret key
        return Jwts.builder()
                .setSubject(username)  // Set the username as the subject of the token
                .claim("roles", roles.stream().map(RoleInfo::getName).collect(Collectors.joining(",")))  // Add roles to the token
                .setIssuedAt(new Date())  // Set the issue time of the token
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))  // Set the expiration time
                .signWith(SECRET_KEY)  // Sign the token with the secret key
                .compact();  // Return the generated token as a compact string
    }

    // Extract username (subject) from the JWT token
    public String extractUserName(String token) {
        // Extract the subject (username) from the token using the helper method
        return extractClaim(token, Claims::getSubject);
    }

    // Extract roles from the JWT token
    public Set<String> extractRoles(String token) {
        // Extract the roles from the token (roles are stored as a comma-separated string)
        String rolesString = extractClaim(token, claims -> claims.get("roles", String.class));
        // Split the roles string and return them as a Set
        return Set.of(rolesString.split(","));
    }

    // Check if the token is valid by attempting to extract all claims
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            // Try extracting all claims to validate the token
            extractAllClaims(token); // If the token is invalid, this will throw an exception
            return true; // If no exception, token is valid
        } catch (JwtException | IllegalArgumentException e) {
            // If any exception occurs, return false indicating invalid token
            return false;
        }
    }

    // Extract all claims from the JWT token
    private Claims extractAllClaims(String token) {
        // Parse the JWT token using the secret key to validate and extract its claims
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)  // Set the secret key for validation
                .build()
                .parseClaimsJws(token)  // Parse and validate the token
                .getBody();  // Extract the claims (body) of the token
    }

    // Extract a specific claim (like expiration date, subject) from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // Extract all claims first
        final Claims claims = extractAllClaims(token);
        // Apply the provided function (e.g., get subject, get roles) to extract the specific claim
        return claimsResolver.apply(claims);
    }

    // Extract the expiration date from the JWT token
    public Date extractExpiration(String token) {
        // Extract the expiration date claim from the token
        return extractClaim(token, Claims::getExpiration);
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        // Check if the token's expiration date is before the current date/time
        return extractExpiration(token).before(new Date());
    }
}
