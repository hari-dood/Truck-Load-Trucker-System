package com.hariSolution.service;

import com.hariSolution.model.UserInfo; // Import UserInfo model
import com.hariSolution.repository.UserRepository; // Import UserRepository to fetch users from the database
import org.springframework.beans.factory.annotation.Autowired; // Autowire annotation to inject dependencies
import org.springframework.security.core.authority.SimpleGrantedAuthority; // Import for assigning roles/authorities to users
import org.springframework.security.core.userdetails.UserDetails; // Import the UserDetails interface for Spring Security
import org.springframework.security.core.userdetails.UserDetailsService; // Interface for custom user details service
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Exception thrown if user not found
import org.springframework.stereotype.Service; // Service annotation for Spring's dependency injection

import java.util.stream.Collectors; // Import for stream processing

@Service // Mark this class as a service component for Spring
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Injecting the UserRepository to access user data

    // Overriding the method to load user details by username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Retrieve user from the repository. If not found, throw UsernameNotFoundException
        UserInfo userInfo = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username '" + username + "' not found"));

        // Return a UserDetails object with the username, password, and authorities (roles)
        return new org.springframework.security.core.userdetails.User(
                userInfo.getUsername(), // Username from UserInfo entity
                userInfo.getPassword(), // Password from UserInfo entity
                userInfo.getRoles().stream() // Stream through the roles of the user
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName())) // Add "ROLE_" prefix to each role
                        .collect(Collectors.toList()) // Collect roles as a list of authorities
        );
    }
}
