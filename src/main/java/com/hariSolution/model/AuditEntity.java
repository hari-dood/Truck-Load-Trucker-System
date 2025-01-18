package com.hariSolution.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@SuperBuilder  // Lombok annotation for generating a builder pattern for this class
@Data  // Lombok annotation for generating getter, setter, toString, equals, and hashCode methods
@NoArgsConstructor  // Lombok annotation to generate a no-args constructor
@AllArgsConstructor  // Lombok annotation to generate a constructor with all fields as parameters
@MappedSuperclass  // Indicates that this class is a superclass for other entities
@Component  // Marks this class as a Spring-managed bean
public class AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremented ID
    private Integer id;

    @Column(updatable = false)  // The createdDate field cannot be updated once set
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime modifiedDate;

    @Column(updatable = false)  // The createdBy field cannot be updated once set
    private String createdBy;

    @Column
    private String modifiedBy;

    // Before the entity is persisted (created), set the createdDate, modifiedDate, createdBy, and modifiedBy fields
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdDate = now;
        this.modifiedDate = now;
        this.createdBy = getCurrentUser();  // Set the user who created this entity
        this.modifiedBy = this.createdBy;  // Initially, the creator is the one who modified the entity
    }

    // Before the entity is updated, update the modifiedDate and modifiedBy fields
    @PreUpdate
    public void preUpdate() {
        this.modifiedDate = LocalDateTime.now();
        this.modifiedBy = getCurrentUser();  // Set the user who modified this entity
    }

    // Helper method to get the currently authenticated user from the security context
    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();  // Return the authenticated username
        }

        throw new IllegalStateException("No authenticated user found");  // Throw an exception if no authenticated user is found
    }
}
