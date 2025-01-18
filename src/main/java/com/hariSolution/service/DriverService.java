package com.hariSolution.service;

import com.hariSolution.mapper.DataResponseMapper; // For mapping response data
import com.hariSolution.mapper.DriverMapper; // For mapping between DriverInfo and DriverInfoDTO
import com.hariSolution.model.DataResponse; // Model for response data format
import com.hariSolution.model.DriverInfo; // Driver information model
import com.hariSolution.DTOs.DriverInfoDTO; // DTO for transferring driver data
import com.hariSolution.repository.DriverRepository; // Repository for interacting with the DriverInfo database
import jakarta.validation.Valid; // Validation annotations
import lombok.RequiredArgsConstructor; // Lombok for automatic constructor generation
import org.springframework.cache.annotation.Cacheable; // Spring caching annotation
import org.springframework.http.HttpStatus; // HTTP status codes
import org.springframework.http.ResponseEntity; // ResponseEntity for HTTP responses
import org.springframework.security.access.prepost.PreAuthorize; // Security annotation for role-based access
import org.springframework.stereotype.Service; // Service annotation for Spring

import java.util.LinkedHashMap; // To hold mapped data
import java.util.List; // List to hold multiple driver info

@Service // Marks the class as a Spring service
@RequiredArgsConstructor // Lombok generates the constructor for required fields
public class DriverService {

    // Injecting dependencies via constructor
    private final DriverRepository driverRepository;
    private final DataResponseMapper responseService;
    private final DriverMapper driverMapper;

    // Method for creating new driver information
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") // Only accessible by users with 'ROLE_ADMIN'
    public ResponseEntity<DataResponse> createDriverInformation(@Valid DriverInfoDTO driverDTO) {
        try {
            // Saving the driver information to the repository
            DriverInfo driverInfo = this.driverRepository.save(driverMapper.toEntity(driverDTO));
            String requestId = String.valueOf(driverInfo.getId()); // Request ID from saved driver

            LinkedHashMap<String, Object> data = new LinkedHashMap<>();

            // Mapping the saved driver entity back to DTO
            DriverInfoDTO Information = this.driverMapper.toDto(driverInfo);
            System.out.println(Information);

            // Adding the driver info to the response data
            data.put("Driver:" + Information.getFullName(), Information);

            // Creating a success response
            DataResponse response = this.responseService.createResponse(data, "success", HttpStatus.OK.value(), HttpStatus.OK, requestId);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Error handling in case of any issues
            return new ResponseEntity<>(this.responseService.createResponse(
                    null,
                    "Error: given bad request 'better luck next time'" + e.getMessage(),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST,
                    "0"
            ), HttpStatus.BAD_REQUEST);
        }
    }

    // Method to retrieve all driver information
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Cacheable(value = "shortLivedCache", key = "#root.methodName", unless = "#result == null") // Cache results for the method
    public DataResponse getDriverAllInformation() {

        List<DriverInfo> driverInfoList = this.driverRepository.findAll(); // Fetch all drivers from the repository

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();

        driverInfoList.forEach(driver -> {
            // Converting each driver entity to DTO
            DriverInfoDTO driverInfoDTO = this.driverMapper.toDto(driver);
            data.put(driver.getFullName(), driverInfoDTO);
        });
        // Returning the response with the data
        return this.responseService.createResponse(data, "success", HttpStatus.OK.value(), HttpStatus.OK, "1");
    }

    // Method to retrieve driver information based on first name
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Cacheable(value = "shortLivedCache", key = "#firstName", unless = "#result == null") // Cache the result
    public DataResponse getDriverFirstName(@Valid String firstName) {

        // Fetch drivers based on the first name (case-insensitive)
        List<DriverInfo> driverInfoList = this.driverRepository.findAllByFirstNameIgnoreCase(firstName);

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();

        driverInfoList.forEach(driver -> {
            // Convert each driver entity to DTO
            DriverInfoDTO driverInfoDTO = this.driverMapper.toDto(driver);
            data.put(driver.getFullName(), driverInfoDTO);
        });

        return this.responseService.createResponse(data, "success", HttpStatus.OK.value(), HttpStatus.OK, "1");
    }

    // Method to retrieve drivers by a list of first names
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Cacheable(value = "shortLivedCache", key = "#firstName", unless = "#result == null")
    public DataResponse findAllByFirstNameInIgnoreCase(@Valid String firstName) {
        // Split comma-separated first names into a list
        List<String> listOfFirstName = List.of(firstName.split(","));

        // Fetch drivers where the first name matches the list (case-insensitive)
        List<DriverInfo> driverInfoList = this.driverRepository.findAllByFirstNameInIgnoreCase(listOfFirstName);

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();

        driverInfoList.forEach(driver -> {
            // Convert each driver to DTO
            DriverInfoDTO driverInfoDTO = this.driverMapper.toDto(driver);
            data.put(driver.getFullName(), driverInfoDTO);
        });

        return this.responseService.createResponse(data, "success", HttpStatus.OK.value(), HttpStatus.OK, "1");
    }

    // Method to update driver information
    public ResponseEntity<DataResponse> updatedDriverInformation(
            Integer id,
            String firstName,
            String fullName,
            String contactNumber,
            String email,
            String experience,
            String licenceNo,
            String aadhaarNo
    ) {
        try {
            // Update driver information in the database using a named query
            int rowsUpdated = driverRepository.updateDriverInfoByIdNamedQuery(id, firstName, fullName, contactNumber, email, experience, licenceNo, aadhaarNo);

            // Retrieve updated driver information
            DriverInfo driverInfo = this.driverRepository.findById(id).get();
            DriverInfoDTO driverInfoDTO = driverMapper.toDto(driverInfo);

            LinkedHashMap<String, Object> data = new LinkedHashMap<>();
            data.put(driverInfoDTO.getFullName(), driverInfoDTO);

            // Return success response
            DataResponse response = this.responseService.createResponse(
                    data, "Driver information updated successfully", HttpStatus.OK.value(), HttpStatus.OK, "1"
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Error handling in case of update failure
            String errorMessage = "Error updating driver information: " + e.getMessage();
            DataResponse errorResponse = this.responseService.createResponse(
                    null, errorMessage, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "0"
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
