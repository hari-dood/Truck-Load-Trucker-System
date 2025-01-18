package com.hariSolution.repository;

import com.hariSolution.model.TripDetails; // Import TripDetails entity
import org.springframework.data.jpa.repository.JpaRepository; // Import JpaRepository for basic CRUD operations
import org.springframework.data.repository.query.Param; // Import Param annotation for query parameters in custom queries
import org.springframework.stereotype.Repository; // Import the Repository annotation for Spring Data JPA

import java.util.List; // Import List for return types of methods

@Repository // Marks this interface as a Spring Data JPA repository
public interface TripRepository extends JpaRepository<TripDetails, Integer> {

    // Method to find all TripDetails by driver's name (case-insensitive)
    List<TripDetails> findAllByDriverNameIgnoreCase(String driverName);

    // Method to find all TripDetails by the month (case-insensitive)
    List<TripDetails> findAllByMonthIgnoreCase(String month);

    // Method to find all TripDetails by the loading point (case-insensitive)
    List<TripDetails> findAllByLoadingPointIgnoreCase(String loadingPoint);

    // Custom method to find TripDetails by load amount using a named query
    // Uses the @Param annotation to pass the loadAmount parameter to the query
    List<TripDetails> findByLoadAmountNamedQuery(@Param("loadAmount") Integer loadAmount);
}
