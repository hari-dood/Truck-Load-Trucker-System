package com.hariSolution.repository;

import com.hariSolution.model.RoleInfo; // Import the RoleInfo entity class
import org.springframework.data.jpa.repository.JpaRepository; // Import JpaRepository for basic CRUD operations
import org.springframework.stereotype.Repository; // Import the Repository annotation for Spring Data JPA

import java.util.Optional; // Import Optional to handle potential null values in the return type

@Repository // Marks this interface as a Spring Data JPA repository.
public interface RoleRepository extends JpaRepository<RoleInfo, Integer> {

   // Custom query method to find a RoleInfo object by its name
   Optional<RoleInfo> findByName(String roleName);
}
