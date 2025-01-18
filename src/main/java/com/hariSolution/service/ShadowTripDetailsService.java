package com.hariSolution.service;

import com.hariSolution.mapper.ShadowTripDetailsMapper; // Mapper to convert between TripDetails and ShadowTripDetails
import com.hariSolution.model.ShadowTripDetails; // Entity representing the shadow trip details
import com.hariSolution.model.TripDetails; // Entity representing the original trip details
import com.hariSolution.repository.ShadowTripRepository; // Repository for interacting with the shadow trip details table
import com.hariSolution.repository.TripRepository; // Repository for interacting with the original trip details table
import jakarta.transaction.Transactional; // Ensures the operations are done within a transaction
import lombok.RequiredArgsConstructor; // Lombok annotation for constructor injection
import org.springframework.data.jpa.repository.Modifying; // Indicates the method modifies the database
import org.springframework.http.HttpStatus; // Enum for HTTP status codes
import org.springframework.http.ResponseEntity; // Represents HTTP response
import org.springframework.stereotype.Service; // Marks this class as a Spring service

import java.util.List;

@Service // Marks this class as a Spring service
@RequiredArgsConstructor // Lombok generates the constructor for the dependencies
public class ShadowTripDetailsService {

    // Injecting required dependencies through constructor
    private final TripRepository tripRepository;
    private final ShadowTripRepository shadowTripRepository;
    private final ShadowTripDetailsMapper shadowTripMapper;

    // Method to copy all trip details from the original table to the shadow table
    @Modifying // Marks the method as modifying the database
    @Transactional // Ensures all database operations are done in a single transaction
    public ResponseEntity<Object> copyAllTripDetailsToTripDetails() {
        // Retrieve all trip details from the original table
        List<TripDetails> tripDetails = this.tripRepository.findAll();

        // For each trip detail, convert it to shadow trip details and save it in the shadow table
        tripDetails.forEach(trip -> {
            ShadowTripDetails shadowTripDetails = shadowTripMapper.toTripDetails(trip);
            shadowTripRepository.save(shadowTripDetails);
        });

        // Return a success message after copying data
        return new ResponseEntity<>("Data copied to Shadow Class!", HttpStatus.OK);
    }

    // Method to copy a specific trip detail to the shadow table
    @Modifying // Marks the method as modifying the database
    @Transactional // Ensures the operation is done within a single transaction
    public ResponseEntity<Object> copyTripDetailsToShadowDetails(TripDetails tripDetails) {
        // Convert the trip details to shadow trip details and save it in the shadow table
        ShadowTripDetails shadowTripDetails = shadowTripMapper.toTripDetails(tripDetails);
        shadowTripRepository.save(shadowTripDetails);

        // Return a success message after copying data
        return new ResponseEntity<>("Data copied to Shadow Class!", HttpStatus.OK);
    }
}
