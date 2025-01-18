package com.hariSolution.configuration;

import com.hariSolution.Jwt.JwtFilter;
import com.hariSolution.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration  // Marks this class as a configuration class
@EnableWebSecurity  // Enables Spring Security for this application
@EnableMethodSecurity  // Enables method-level security annotations such as @PreAuthorize
public class SecurityConfig  {

    private final JwtFilter jwtFilter;  // A reference to the custom JWT filter to intercept requests

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;  // Constructor injection of JwtFilter
    }

    // Bean definition for UserDetailsService, which is used for authentication purposes
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(); // Creates a custom implementation of UserDetailsService
    }

    // Bean definition for SecurityFilterChain that configures HTTP security settings
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disables CSRF protection for stateless APIs, as no session is maintained
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints that are accessible without authentication
                        .requestMatchers("/api/v1/welcome", "/auth/register", "/auth/login", "/api/v1/file/**", "/api/v1/invoice/**").permitAll()

                        // Endpoints accessible only by users with 'ROLE_USER'
                        .requestMatchers("/api/v1/user/**").hasAuthority("ROLE_USER")

                        // Admin endpoints accessible only by users with 'ROLE_ADMIN'
                        .requestMatchers("/api/v1/admin/**").hasAuthority("ROLE_ADMIN")

                        // Any other request requires authentication
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess
                        // Configures the application to be stateless, meaning no session is created
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider()) // Uses a custom authentication provider
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Adds the custom JWT filter before UsernamePasswordAuthenticationFilter
        return http.build();  // Builds and returns the configured SecurityFilterChain
    }

    // Bean definition for AuthenticationProvider that uses a custom user details service
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService()); // Sets the custom user details service
        authenticationProvider.setPasswordEncoder(passwordEncoder()); // Sets the password encoder
        return authenticationProvider;
    }

    // Bean definition for PasswordEncoder, which hashes and verifies passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Uses BCrypt for password hashing
    }

    // Bean definition for AuthenticationManager, which is used to authenticate users
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();  // Returns the authentication manager from the configuration
    }
}
