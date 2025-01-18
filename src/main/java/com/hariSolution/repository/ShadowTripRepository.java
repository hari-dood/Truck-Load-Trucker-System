package com.hariSolution.repository;

import com.hariSolution.model.ShadowTripDetails; // Import the ShadowTripDetails entity class
import org.springframework.data.jpa.repository.JpaRepository; // Import JpaRepository for basic CRUD operations
import org.springframework.stereotype.Repository; // Import the Repository annotation for Spring Data JPA

@Repository // Marks this interface as a Spring Data JPA repository
public interface ShadowTripRepository extends JpaRepository<ShadowTripDetails, Integer> {
    // No additional methods defined, JpaRepository provides basic CRUD operations by default
}
