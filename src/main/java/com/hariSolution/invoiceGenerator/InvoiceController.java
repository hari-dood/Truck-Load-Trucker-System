package com.hariSolution.invoiceGenerator;

import com.hariSolution.DTOs.TripDetailsDTO;
import com.hariSolution.service.InvoiceCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;

@RestController // Marks the class as a REST controller, which means it handles HTTP requests and responses.
@RequestMapping("/api/v1/invoice") // Specifies the base URL path for all endpoints in this controller.
@RequiredArgsConstructor // Lombok annotation that automatically generates a constructor for required fields (InvoiceCreateService)
public class InvoiceController {

    private final InvoiceCreateService invoiceCreateService; // Dependency injection of the InvoiceCreateService to handle invoice creation

    // Endpoint to generate and download an invoice based on a trip ID
    @GetMapping("/{trip-id}") // Handles GET requests where "trip-id" is a path parameter
    public ResponseEntity<InputStreamResource> createInvoice(@PathVariable("trip-id") Integer trip_id) throws MalformedURLException, FileNotFoundException {

        // Call the service layer to generate a PDF invoice as a ByteArrayInputStream
        ByteArrayInputStream pdfStream = this.invoiceCreateService.createInvoiceCreation(trip_id);

        // Create HTTP headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, // This header indicates that the file should be downloaded
                "attachment; filename=invoice_trip_" + trip_id + "_" + LocalDateTime.now() + ".pdf"); // Dynamically generate the filename with the trip ID and current timestamp

        // Return a ResponseEntity with the PDF file as an InputStreamResource and proper headers
        return ResponseEntity.ok()
                .headers(headers) // Set the headers
                .contentType(MediaType.APPLICATION_PDF) // Set the content type to PDF
                .body(new InputStreamResource(pdfStream)); // Set the response body as the PDF InputStreamResource
    }
}
