package com.hariSolution.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    // Public endpoint accessible by anyone
    @GetMapping("/welcomeHome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    // User-specific endpoint with security check: Only the authenticated user can access their profile
    @GetMapping("/user/userProfile")
    @PostAuthorize("returnObject.contains(authentication.name)")  // Post-execution security check
    public String userProfile() {
        // Accessing the name of the authenticated user from the security context
        return "Welcome to User Profile for: " + SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // Admin-specific endpoint with security check: Only users with 'ROLE_ADMIN' can access this
    @GetMapping("/admin/adminProfile")
    @PostAuthorize("hasAuthority('ROLE_ADMIN')")  // Post-execution security check
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }
}
