package com.hariSolution.controller;

import com.hariSolution.DTOs.TripDetailsDTO;
import com.hariSolution.mapper.DataResponseMapper;
import com.hariSolution.model.DataRequest;
import com.hariSolution.model.DataResponse;
import com.hariSolution.model.TripDetails;
import com.hariSolution.pdfGenerator.PdfGeneratorService;
import com.hariSolution.repository.TripRepository;
import com.hariSolution.service.TripDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trips-details")
public class TripDetailsController {

    private final TripDetailsService tripDetailsService;  // Service handling trip details-related logic
    private final TripRepository tripRepository;  // Repository for accessing trip details data from the database
    private final PdfGeneratorService pdfService;  // Service to handle PDF generation logic

    // Endpoint to create a new trip information
    @PostMapping("/createTrip")  // Maps POST requests to /api/v1/trips-details/createTrip
    public ResponseEntity<DataResponse> createTripInformation(@RequestBody @Valid DataRequest dataRequest) {
        // The @Valid annotation ensures the input data is validated based on the DataRequest constraints
        // The service is called to handle the creation of trip information
        return this.tripDetailsService.createTripInformation(dataRequest);
    }

    // Endpoint to get all trip details information
    @GetMapping("/get-allTrips")  // Maps GET requests to /api/v1/trips-details/get-allTrips
    public ResponseEntity<DataResponse> getAllTripInformation() {
        // Calls the service to fetch all trip details and returns the response
        DataResponse response = this.tripDetailsService.getAllTripInformation();
        return ResponseEntity.ok(response);
    }

    // Endpoint to get trip information by driver's name
    @GetMapping("/get-trips/{driverName}")  // Maps GET requests to /api/v1/trips-details/get-trips/{driverName}
    public ResponseEntity<DataResponse> getTripInformationByDriverName(@PathVariable("driverName") String driverName) {
        // Calls the service to fetch trips by driver's name
        DataResponse response = this.tripDetailsService.getTripInformationByDriverInfo(driverName);
        return ResponseEntity.ok(response);
    }

    // Endpoint to get trips for a specific month (admin access)
    @GetMapping("/admin/trips-in/{month}")  // Maps GET requests to /api/v1/trips-details/admin/trips-in/{month}
    public ResponseEntity<DataResponse> getTripInformationMonth(@PathVariable("month") String month) {
        // Calls the service to fetch trips for the given month
        DataResponse response = this.tripDetailsService.getTripInformationMonth(month);
        return ResponseEntity.ok(response);
    }

    // Endpoint to get trips based on loading point
    @GetMapping("/trips-on/{loadingPoint}")  // Maps GET requests to /api/v1/trips-details/trips-on/{loadingPoint}
    public ResponseEntity<DataResponse> getTripInformationLoadingPoint(@PathVariable("loadingPoint") String loadingPoint) {
        // Calls the service to fetch trips based on the provided loading point
        DataResponse response = this.tripDetailsService.getTripInformationLoadingPoint(loadingPoint);
        return ResponseEntity.ok(response);
    }

    // Endpoint to get trips with load amount greater than or equal to a specified amount
    @GetMapping("/greater_than_or_equal_to/loadAmount")  // Maps GET requests to /api/v1/trips-details/greater_than_or_equal_to/loadAmount
    public ResponseEntity<DataResponse> getTripInformationLoadAmount(@RequestParam("loadAmount") Integer loadAmount) {
        // Calls the service to fetch trips with load amount greater than or equal to the specified amount
        DataResponse response = this.tripDetailsService.getTripInformationLoadAmount(loadAmount);
        return ResponseEntity.ok(response);
    }

    // Endpoint to update existing trip details
    @PutMapping("/update-tripDetails/{id}")  // Maps PUT requests to /api/v1/trips-details/update-tripDetails/{id}
    public ResponseEntity<DataResponse> updatedTripDetails(@PathVariable("id") Integer trip_id, @RequestBody DataRequest request) {
        // Calls the service to update the trip details based on the provided ID
        DataResponse response = this.tripDetailsService.updateTripDetails(trip_id, request);
        return ResponseEntity.ok(response);
    }

    // Endpoint to delete trip details by ID
    @DeleteMapping("/delete-tripDetails/{id}")  // Maps DELETE requests to /api/v1/trips-details/delete-tripDetails/{id}
    public ResponseEntity<DataResponse> deleteTripDetails(@PathVariable("id") Integer id) {
        // Calls the service to delete the trip details based on the provided ID
        DataResponse response = this.tripDetailsService.deleteTripDetails(id);
        return ResponseEntity.ok(response);
    }

    // Endpoint to download all trip information in Excel format
    @GetMapping("/download/xl")  // Maps GET requests to /api/v1/trips-details/download/xl
    public ResponseEntity<InputStreamResource> downloadAllTripInformationExcel() throws IOException {
        // Generates Excel file containing all trip details
        String fileName = "Trip_details_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".xlsx";
        ByteArrayInputStream inputStream = this.tripDetailsService.downloadAllTripInformationExcel();
        InputStreamResource resource = new InputStreamResource(inputStream);

        // Returns the Excel file as a downloadable resource
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    // Endpoint to generate a PDF report for all trips
    @GetMapping("/download/pdf")  // Maps GET requests to /api/v1/trips-details/download/pdf
    public ResponseEntity<InputStreamResource> generateTripsInformationPdfReport() {
        // Fetches all trip details
        List<TripDetails> tripDetails = tripRepository.findAll();

        // If no trip details are available, throws an exception
        if (tripDetails.isEmpty()) {
            throw new RuntimeException("No trip data available to generate the report.");
        }

        // Generates a PDF containing all trip details
        ByteArrayInputStream pdfData = pdfService.DataToPdf(tripDetails);

        // Creates the HTTP headers for the PDF download
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=trips_information_report_" + LocalDateTime.now() + ".pdf");

        // Returns the generated PDF as a downloadable resource
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfData));
    }

    // Endpoint to sort trips by a specified field
    @GetMapping("/filter/{filed}")  // Maps GET requests to /api/v1/trips-details/filter/{filed}
    public ResponseEntity<DataResponse> getTripBySorting(@PathVariable("filed") String filed) {
        // Calls the service to get trips sorted by the specified field
        DataResponse response = this.tripDetailsService.getTripBySorting(filed);
        return ResponseEntity.ok(response);
    }

    // Endpoint for pagination (get trips with a specified offset and page size)
    @GetMapping("pagination/{offset}/{pageSize}")  // Maps GET requests to /api/v1/trips-details/pagination/{offset}/{pageSize}
    public Page<TripDetailsDTO> getTripsWithPagination(@PathVariable("offset") int offset, @PathVariable("pageSize") int pageSize) {
        // Calls the service to return paginated trip details
        return this.tripDetailsService.getTripsWithPagination(offset, pageSize);
    }

    // Endpoint for pagination with sorting (get trips with a specified offset, page size, and field for sorting)
    @GetMapping("pagination-sorting/{offset}/{pageSize}/{filed}")  // Maps GET requests to /api/v1/trips-details/pagination-sorting/{offset}/{pageSize}/{filed}
    public Page<TripDetailsDTO> getTripsWithPaginationWithSorting(@PathVariable("offset") int offset, @PathVariable("pageSize") int pageSize, @PathVariable("filed") String filed) {
        // Calls the service to return paginated and sorted trip details
        return this.tripDetailsService.getTripsWithPaginationWithSorting(offset, pageSize, filed);
    }
}
