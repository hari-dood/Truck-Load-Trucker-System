package com.hariSolution.controller;

import com.hariSolution.mapper.DataResponseMapper;
import com.hariSolution.model.DataResponse;
import com.hariSolution.DTOs.DriverInfoDTO;
import com.hariSolution.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/driver")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;
    private final DataResponseMapper responseService;

    @PostMapping("/create")
    public ResponseEntity<DataResponse> createDriverInformation(@RequestBody @Valid DriverInfoDTO driverDTO) {
        // Calls the service to handle driver creation logic
        return driverService.createDriverInformation(driverDTO);
    }

    @GetMapping("/get-allDrivers")
    public ResponseEntity<DataResponse> getDriverAllInformation() {
        // Calls the service to fetch all driver information
        DataResponse response = driverService.getDriverAllInformation();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{firstName}")
    public ResponseEntity<DataResponse> getDriverFirstName(@PathVariable("firstName") @Valid String firstName) {
        // Calls the service to fetch driver information by firstName
        DataResponse response = driverService.getDriverFirstName(firstName);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/in/{firstName}")
    public ResponseEntity<DataResponse> findAllByFirstNameInIgnoreCase(@PathVariable("firstName") @Valid String firstName) {
        // Calls the service to fetch drivers based on firstName in ignore case
        DataResponse response = driverService.findAllByFirstNameInIgnoreCase(firstName);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<DataResponse> updateDriverInformation(
            @PathVariable("id") Integer id,
            @RequestParam("firstName") String firstName,
            @RequestParam("fullName") String fullName,
            @RequestParam("contactNumber") String contactNumber,
            @RequestParam("email") String email,
            @RequestParam("experience") String experience,
            @RequestParam("licenceNo") String licenceNo,
            @RequestParam("aadhaarNo") String aadhaarNo
    ) {
        // Calls the service to update driver information
        return driverService.updatedDriverInformation(id, firstName, fullName, contactNumber, email, experience, licenceNo, aadhaarNo);
    }
}
