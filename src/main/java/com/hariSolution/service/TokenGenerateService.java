package com.hariSolution.service;

import com.hariSolution.Jwt.JwtUtil;
import com.hariSolution.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenGenerateService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Cacheable(value = "shortLivedCache", key = "#loginRequest.username", unless = "#result == null", cacheManager = "cacheManager")
    public String generateToken(UserInfo loginRequest){
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        // Generate token after successful authentication
        return jwtUtil.generateToken(loginRequest.getUsername());
    }
}
