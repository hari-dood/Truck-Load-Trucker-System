package com.hariSolution.controller;

import com.hariSolution.mapper.DataResponseMapper;
import com.hariSolution.model.DataResponse;
import com.hariSolution.DTOs.DriverInfoDTO;
import com.hariSolution.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController  // Marks the class as a Spring REST controller to handle HTTP requests
@RequestMapping("/api/v1/driver")  // Specifies the base URL path for all the endpoints in this controller
@RequiredArgsConstructor  // Automatically generates a constructor for required final fields (driverService and responseService)
public class DriverController {

    private final DriverService driverService;  // DriverService handles business logic related to driver operations
    private final DataResponseMapper responseService;  // Maps the response into a DataResponse format for consistent response structure

    // Endpoint for creating driver information
    @PostMapping("/create")  // Maps POST requests sent to /api/v1/driver/create
    public ResponseEntity<DataResponse> createDriverInformation(@RequestBody @Valid DriverInfoDTO driverDTO) {
        // The @Valid annotation ensures that the input data in the request body is validated against the DriverInfoDTO class constraints
        // The method calls the driverService to handle driver creation logic
        return driverService.createDriverInformation(driverDTO);
    }

    // Endpoint to get information of all drivers
    @GetMapping("/get-allDrivers")  // Maps GET requests to /api/v1/driver/get-allDrivers
    public ResponseEntity<DataResponse> getDriverAllInformation() {
        // Calls the driverService to retrieve all driver information from the database
        DataResponse response = driverService.getDriverAllInformation();
        return ResponseEntity.ok(response);  // Returns a successful response with HTTP status 200 (OK)
    }

    // Endpoint to get driver information by first name
    @GetMapping("/get/{firstName}")  // Maps GET requests to /api/v1/driver/get/{firstName}
    public ResponseEntity<DataResponse> getDriverFirstName(@PathVariable("firstName") @Valid String firstName) {
        // The @PathVariable annotation binds the {firstName} part of the URL to the method parameter
        // @Valid ensures the value of the firstName is validated
        // Retrieves driver information based on the provided first name and returns the data in a response
        DataResponse response = driverService.getDriverFirstName(firstName);
        return ResponseEntity.ok(response);  // Returns the driver data with HTTP status 200 (OK)
    }

    // Endpoint to find all drivers with a first name (case-insensitive)
    @GetMapping("/in/{firstName}")  // Maps GET requests to /api/v1/driver/in/{firstName}
    public ResponseEntity<DataResponse> findAllByFirstNameInIgnoreCase(@PathVariable("firstName") @Valid String firstName) {
        // This method fetches all drivers whose first name matches the input string, ignoring case
        // The @PathVariable annotation binds the URL parameter to the method argument
        DataResponse response = driverService.findAllByFirstNameInIgnoreCase(firstName);
        return ResponseEntity.ok(response);  // Returns the response containing the matched drivers with HTTP status 200 (OK)
    }

    // Endpoint to update driver information
    @PostMapping("/update/{id}")  // Maps POST requests to /api/v1/driver/update/{id}
    public ResponseEntity<DataResponse> updateDriverInformation(
            @PathVariable("id") Integer id,  // Extracts the driver ID from the URL path
            @RequestParam("firstName") String firstName,  // Extracts the first name from the request parameters
            @RequestParam("fullName") String fullName,  // Extracts the full name from the request parameters
            @RequestParam("contactNumber") String contactNumber,  // Extracts the contact number from the request parameters
            @RequestParam("email") String email,  // Extracts the email from the request parameters
            @RequestParam("experience") String experience,  // Extracts the experience from the request parameters
            @RequestParam("licenceNo") String licenceNo,  // Extracts the driver's license number from the request parameters
            @RequestParam("aadhaarNo") String aadhaarNo  // Extracts the Aadhaar number from the request parameters
    ) {
        // Calls the driverService to update the driver information with the given details
        // The driver ID is provided as part of the URL, and other details are passed as request parameters
        return driverService.updatedDriverInformation(id, firstName, fullName, contactNumber, email, experience, licenceNo, aadhaarNo);
    }
}
