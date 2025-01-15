package com.hariSolution.service;

import com.hariSolution.mapper.DataResponseMapper;
import com.hariSolution.mapper.DriverMapper;
import com.hariSolution.model.DataResponse;
import com.hariSolution.model.DriverInfo;
import com.hariSolution.DTOs.DriverInfoDTO;
import com.hariSolution.repository.DriverRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
    private final DataResponseMapper responseService;
    private final DriverMapper driverMapper;

    // @Cacheable(value = "driverInfoCache", key = "#driverDTO.id")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<DataResponse> createDriverInformation(@Valid DriverInfoDTO driverDTO) {
        try {

            DriverInfo driverInfo = this.driverRepository.save(driverMapper.toEntity(driverDTO));
            String requestId = String.valueOf(driverInfo.getId());

            LinkedHashMap<String, Object> data = new LinkedHashMap<>();

            DriverInfoDTO Information = this.driverMapper.toDto(driverInfo);
            System.out.println(Information);


            data.put("Driver:" + Information.getFullName(), Information);


            DataResponse response = this.responseService.createResponse(data, "success", HttpStatus.OK.value(), HttpStatus.OK, requestId);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return new ResponseEntity<>(this.responseService.createResponse(null, "Error: given bad request 'better luck next time'" + e.getMessage(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "0"), HttpStatus.BAD_REQUEST);
        }

    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Cacheable(value = "shortLivedCache", key = "#root.methodName", unless = "#result == null")
    public DataResponse getDriverAllInformation() {

        List<DriverInfo> driverInfoList = this.driverRepository.findAll();

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();

        driverInfoList.forEach(driver -> {
            DriverInfoDTO driverInfoDTO = this.driverMapper.toDto(driver);
            data.put(driver.getFullName(), driverInfoDTO);
        });
        return this.responseService.createResponse(data, "success", HttpStatus.OK.value(), HttpStatus.OK, "1");

    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Cacheable(value = "shortLivedCache", key = "#firstName", unless = "#result == null")
    public DataResponse getDriverFirstName(@Valid String firstName) {


        List<DriverInfo> driverInfoList = this.driverRepository.findAllByFirstNameIgnoreCase(firstName);

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();

        driverInfoList.forEach(driver -> {

            DriverInfoDTO driverInfoDTO = this.driverMapper.toDto(driver);

            data.put(driver.getFullName(), driverInfoDTO);
        });

        return this.responseService.createResponse(data, "success", HttpStatus.OK.value(), HttpStatus.OK, "1");

    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Cacheable(value = "shortLivedCache", key = "#firstName", unless = "#result == null")
    public DataResponse findAllByFirstNameInIgnoreCase(@Valid String firstName) {

            List<String> listOfFirstName = List.of(firstName.split(","));

            List<DriverInfo> driverInfoList = this.driverRepository.findAllByFirstNameInIgnoreCase(listOfFirstName);

            LinkedHashMap<String, Object> data = new LinkedHashMap<>();

            driverInfoList.forEach(driver -> {
                DriverInfoDTO driverInfoDTO = this.driverMapper.toDto(driver);
                data.put(driver.getFullName(), driverInfoDTO);
            });

        return this.responseService.createResponse(data, "success", HttpStatus.OK.value(), HttpStatus.OK, "1");




    }

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

            int rowsUpdated = driverRepository.updateDriverInfoByIdNamedQuery(id, firstName, fullName, contactNumber, email, experience, licenceNo, aadhaarNo);
            DriverInfo driverInfo = this.driverRepository.findById(id).get();
            DriverInfoDTO driverInfoDTO = driverMapper.toDto(driverInfo);

            LinkedHashMap<String, Object> data = new LinkedHashMap<>();
            data.put(driverInfoDTO.getFullName(), driverInfoDTO);


            // Create a success response
            DataResponse response = this.responseService.createResponse(
                    data, "Driver information updated successfully", HttpStatus.OK.value(), HttpStatus.OK, "1"
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Create an error response in case of an exception
            String errorMessage = "Error updating driver information: " + e.getMessage();
            DataResponse errorResponse = this.responseService.createResponse(
                    null, errorMessage, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "0"
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

    }
}

