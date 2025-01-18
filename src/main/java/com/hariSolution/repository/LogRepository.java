package com.hariSolution.repository;

import com.hariSolution.model.LogInfo; // Import the LogInfo entity class
import org.springframework.data.jpa.repository.JpaRepository; // Import JpaRepository for basic CRUD operations
import org.springframework.stereotype.Repository; // Import the Repository annotation for Spring Data JPA

@Repository // Marks this interface as a Spring Data JPA repository.
public interface LogRepository extends JpaRepository<LogInfo, Long> {
    // JpaRepository provides all the basic CRUD operations for LogInfo entity (like save, findById, delete, etc.).
    // The 'Long' is the type of the ID of the entity (LogInfo in this case).
}
