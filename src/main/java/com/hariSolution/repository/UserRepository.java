package com.hariSolution.repository;

import com.hariSolution.model.UserInfo; // Import UserInfo entity
import org.springframework.data.jpa.repository.JpaRepository; // Import JpaRepository for CRUD operations
import org.springframework.stereotype.Repository; // Import Repository annotation for Spring Data JPA

import java.util.Optional; // Import Optional for handling potential null return values

@Repository // Marks this interface as a Spring Data JPA repository
public interface UserRepository extends JpaRepository<UserInfo, Integer> {

    // Method to find a UserInfo by the username
    // Returns an Optional<UserInfo> to handle cases where the user may not be found
    Optional<UserInfo> findByUsername(String username);
}
