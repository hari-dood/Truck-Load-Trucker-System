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

    @GetMapping("/welcomeHome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @GetMapping("/user/userProfile")
    @PostAuthorize("returnObject.contains(authentication.name)")
    public String userProfile() {
        // Simulating dynamic return based on the authenticated user
        return "Welcome to User Profile for: " + SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/admin/adminProfile")
    @PostAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        // Admin-specific data
        return "Welcome to Admin Profile";
    }



}
