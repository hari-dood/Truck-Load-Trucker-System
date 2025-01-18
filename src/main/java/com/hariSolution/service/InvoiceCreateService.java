package com.hariSolution.service;

import com.hariSolution.DTOs.TripDetailsDTO; // DTO representing trip details
import com.hariSolution.invoiceGenerator.InvoiceService; // Service for generating invoices
import com.hariSolution.mapper.TripDetailsMapper; // Mapper for converting between TripDetails entity and DTO
import com.hariSolution.model.TripDetails; // Entity representing trip details
import com.hariSolution.repository.TripRepository; // Repository for accessing trip details in the database
import lombok.RequiredArgsConstructor; // Lombok annotation for constructor injection
import org.springframework.stereotype.Service; // Spring annotation to define a service class

import java.io.ByteArrayInputStream; // Used to return invoice as byte stream
import java.io.FileNotFoundException; // Exception for file not found
import java.net.MalformedURLException; // Exception for invalid URL format

@Service // Marks this class as a Spring service
@RequiredArgsConstructor // Lombok will generate a constructor to inject required dependencies
public class InvoiceCreateService {

    // Dependencies injected via the constructor
    private final TripRepository tripRepository;
    private final TripDetailsMapper tripDetailsMapper;
    private final InvoiceService invoiceService;

    // Method to create an invoice for a given trip ID
    public ByteArrayInputStream createInvoiceCreation(Integer trip_id) throws MalformedURLException, FileNotFoundException {
        // Fetch trip details from the repository using the trip ID
        TripDetails tripDetails = this.tripRepository.findById(trip_id)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

        // Map the TripDetails entity to a DTO
        TripDetailsDTO dto = this.tripDetailsMapper.toDto(tripDetails);

        // Delegate invoice creation to the InvoiceService and return the generated invoice as a byte stream
        return this.invoiceService.invoiceCreation(dto);
    }
}
