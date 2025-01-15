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

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final int JWT_EXPIRATION_MS = 86400000; // 24 hours in milliseconds

    private final UserRepository userRepository;

    public JwtUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Method to generate JWT token
    public String generateToken(String username) {
        Optional<UserInfo> userInfo = userRepository.findByUsername(username);

        if (userInfo.isEmpty()) {
            throw new IllegalArgumentException("User not found: " + username);
        }

        List<RoleInfo> roles = userInfo.get().getRoles();

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles.stream().map(RoleInfo::getName).collect(Collectors.joining(",")))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))
                .signWith(SECRET_KEY)
                .compact();
    }

    // Extract username from token
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract roles from token
    public Set<String> extractRoles(String token) {
        String rolesString = extractClaim(token, claims -> claims.get("roles", String.class));
        return Set.of(rolesString.split(","));
    }

    // Check if the token is valid
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            extractAllClaims(token); // If the token is invalid, this will throw an exception
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract a specific claim from the token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extract expiration date from the token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
