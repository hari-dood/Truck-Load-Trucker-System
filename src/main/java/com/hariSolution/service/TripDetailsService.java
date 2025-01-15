package com.hariSolution.service;

import com.hariSolution.DTOs.TripDetailsDTO;
import com.hariSolution.mapper.DataRequestMapper;
import com.hariSolution.mapper.DataResponseMapper;
import com.hariSolution.mapper.TripDetailsMapper;
import com.hariSolution.model.*;
import com.hariSolution.notification.MailNotificationDisplay;
import com.hariSolution.repository.TripRepository;
import com.hariSolution.xlGenerator.ExcelGeneratorService;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(TripDetailsService.class);

    private final TripRepository tripRepository;
    private final TripDetailsMapper tripMapper;
    private final DataResponseMapper responseService;
    private final DataRequestMapper requestMapper;
    private final JavaMailSender mailSender;
    private final ShadowTripDetailsService shadowTripService;
    private final ExcelGeneratorService excelService;
    private final MailNotificationDisplay mailService;

    @Value("${email.sender}")  // Get sender email from application properties
    private String emailSender;

    @Value("${email.recipient}")  // Get recipient email from application properties
    private String emailRecipient;

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<DataResponse> createTripInformation(@Valid DataRequest dataRequest) {
        try {
            logger.info("Starting createTripInformation with DataRequest: {}", dataRequest);

            TripDetailsDTO tripDetailsDTO = requestMapper.toTripDto(dataRequest);
            TripDetails tripDetails = tripMapper.toEntity(tripDetailsDTO);
            tripRepository.save(tripDetails);
            logger.info("Saved TripDetails to DB with ID: {}", tripDetails.getId());

            TripDetailsDTO tripDTO = tripMapper.toDto(tripDetails);

            LinkedHashMap<String, Object> data = new LinkedHashMap<>();
            data.put("tripDetails", tripDTO);

            DataResponse response = responseService.createResponse(
                    data, "Successfully trip details have been included",
                    HttpStatus.OK.value(), HttpStatus.OK, tripDetails.getId().toString()
            );

            sendEmail(tripDTO);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error creating trip information", e);

            String errorMessage = "Error creating trip information: " + e.getMessage();
            DataResponse errorResponse = responseService.createResponse(
                    null, errorMessage, HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST, ""
            );

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    private void sendEmail(TripDetailsDTO tripDTO) {
        try {
            if (emailSender == null || emailRecipient == null) {
                logger.warn("Email sender or recipient not configured. Skipping email.");
                return;
            }

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setFrom(emailSender);
            messageHelper.setTo(emailRecipient);
            messageHelper.setSubject("Trip Details Information");
            messageHelper.setText(mailService.formatTripDetails(tripDTO), true);

            mailSender.send(mimeMessage);
            logger.info("Email sent successfully to: {}", emailRecipient);

        } catch (Exception e) {
            logger.error("Failed to send email", e);
        }
    }


    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @Cacheable(value = "shortLivedCache", key = "#root.methodName")
    public DataResponse getAllTripInformation() {
        try {
            List<TripDetails> tripDetails = tripRepository.findAll();
            LinkedHashMap<String, Object> data = new LinkedHashMap<>();
            tripDetails.forEach(trip -> {
                TripDetailsDTO tripDTO = tripMapper.toDto(trip);
                data.put("Trip_" + trip.getId(), tripDTO);
            });

            return responseService.createResponse(
                    data, "Successfully fetched all trip details",
                    HttpStatus.OK.value(), HttpStatus.OK, "1"
            );

        } catch (Exception e) {
            logger.error("Error fetching all trip information", e);
            return responseService.createResponse(
                    null, "Error fetching trip information: " + e.getMessage(),
                    HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, ""
            );
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @Cacheable(value = "shortLivedCache", key = "#driverName")
    public DataResponse getTripInformationByDriverInfo(String driverName) {
        try {
            // Fetch trip details from the repository
            List<TripDetails> tripDetails = this.tripRepository.findAllByDriverNameIgnoreCase(driverName);

            // Prepare the response data
            LinkedHashMap<String, Object> data = new LinkedHashMap<>();
            tripDetails.forEach(trip -> {
                TripDetailsDTO tripDTO = tripMapper.toDto(trip);
                data.put("Trip_" + trip.getId(), tripDTO);
            });

            // Create the response object
            DataResponse response = this.responseService.createResponse(
                    data,
                    "Successfully trip details have been fetched",
                    HttpStatus.OK.value(),
                    HttpStatus.OK,
                    "1"
            );

            // Log cache hit or miss
            logger.info("Returning trip details: {}", response);
            return response;

        } catch (Exception e) {
            String errorMessage = "Error getting trip information: " + e.getMessage();

            // Return error response
            return this.responseService.createResponse(
                    null,
                    errorMessage,
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND,
                    ""
            );
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Cacheable(value = "shortLivedCache", key = "#month")
    //@Cacheable(value = "tripInformationByMonthCache", key = "#month")
    public DataResponse getTripInformationMonth(String month) {

        try {
            List<TripDetails> tripDetails = this.tripRepository.findAllByMonthIgnoreCase(month);

            LinkedHashMap<String, Object> data = new LinkedHashMap<>();
            tripDetails.forEach(trip -> {
                TripDetailsDTO tripDTO = tripMapper.toDto(trip);
                data.put("Trip_" + trip.getId(), tripDTO);
            });

            DataResponse response = this.responseService.createResponse(
                    data, "Successfully trip details have been fetched",
                    HttpStatus.OK.value(), HttpStatus.OK, "1");

            // Log cache hit or miss
            logger.info("Returning data from cache or processing {}", response);
            return response;

        } catch (Exception e) {
            String errorMessage = "Error getting trip information: " + e.getMessage();

            return this.responseService.createResponse(
                    null, errorMessage, HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND, "");
        }


    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Cacheable(value = "shortLivedCache", key = "#loadingPoint")
    public DataResponse getTripInformationLoadingPoint(String loadingPoint) {
        try {
            List<TripDetails> tripDetails = this.tripRepository.findAllByLoadingPointIgnoreCase(loadingPoint);

            LinkedHashMap<String, Object> data = new LinkedHashMap<>();
            tripDetails.forEach(trip -> {
                TripDetailsDTO tripDTO = tripMapper.toDto(trip);
                data.put("Trip_" + trip.getId(), tripDTO);
            });

            DataResponse response = this.responseService.createResponse(
                    data, "Successfully trip details have been fetched",
                    HttpStatus.OK.value(), HttpStatus.OK, "1");

            // Log cache hit or miss
            logger.info("Returning data from cache or processing {}", response);
            return response;

        } catch (Exception e) {
            String errorMessage = "Error getting trip information: " + e.getMessage();

            return this.responseService.createResponse(
                    null, errorMessage, HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND, "");
        }

    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Cacheable(value = "shortLivedCache", key = "#loadAmount")
    //@Cacheable(value = "tripInformationByLoadAmountCache", key = "#loadAmount")
    public DataResponse getTripInformationLoadAmount(Integer loadAmount) {

        try {
            List<TripDetails> tripDetails = this.tripRepository.findByLoadAmountNamedQuery(loadAmount);

            LinkedHashMap<String, Object> data = new LinkedHashMap<>();
            tripDetails.forEach(trip -> {
                TripDetailsDTO tripDTO = tripMapper.toDto(trip);
                data.put("Trip_" + trip.getId(), tripDTO);
            });

            DataResponse response = this.responseService.createResponse(
                    data, "Successfully trip details have been fetched",
                    HttpStatus.OK.value(), HttpStatus.OK, "1");

            // Log cache hit or miss
            logger.info("Returning data from cache or processing {}", response);
            return response;

        } catch (Exception e) {
            String errorMessage = "Error getting trip information: " + e.getMessage();

            return this.responseService.createResponse(
                    null, errorMessage, HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND, "");
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @CachePut(cacheNames = "shortLivedCache", key = "#id")
    //@CachePut(cacheNames = "updatedTripDetails", key = "#id")
    public DataResponse updateTripDetails(Integer id, DataRequest request) {
        try {
            TripDetails existingTrip = tripRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Trip not found for ID: " + id));

            shadowTripService.copyTripDetailsToShadowDetails(existingTrip);

            TripDetailsDTO tripDto = requestMapper.toTripDto(request);
            TripDetails updatedTrip = tripMapper.toEntity(tripDto);
            updatedTrip.setId(existingTrip.getId());
            tripRepository.save(updatedTrip);

            LinkedHashMap<String, Object> data = new LinkedHashMap<>();
            data.put("Trip_" + updatedTrip.getId(), tripDto);

            logger.info("Successfully updated trip details: {}", updatedTrip.getId());
            return responseService.createResponse(
                    data, "Successfully updated trip details.",
                    HttpStatus.OK.value(), HttpStatus.OK, "1"
            );

        } catch (Exception e) {
            logger.error("Error updating trip information for ID: {}", id, e);
            return responseService.createResponse(
                    null, "Error updating trip information: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, ""
            );
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @CacheEvict(cacheNames = "shortLivedCache", key = "#id", beforeInvocation = true)
    //@CacheEvict(cacheNames = "deleteTripDetails", key = "#id", beforeInvocation = true)
    public DataResponse deleteTripDetails(Integer id) {
        try {
            TripDetails existingTrip = tripRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Trip not found for ID: " + id));

            shadowTripService.copyTripDetailsToShadowDetails(existingTrip);
            tripRepository.delete(existingTrip);

            LinkedHashMap<String, Object> data = new LinkedHashMap<>();
            data.put("DeletedTripDetails", existingTrip);

            logger.info("Successfully deleted trip details for ID: {}", id);
            return responseService.createResponse(
                    data, "Successfully deleted trip information",
                    HttpStatus.OK.value(), HttpStatus.OK, "1"
            );

        } catch (Exception e) {
            logger.error("Error deleting trip information for ID: {}", id, e);
            return responseService.createResponse(
                    null, "Error deleting trip information: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, ""
            );
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ByteArrayInputStream downloadAllTripInformationExcel() throws IOException {
        try {

            List<TripDetails> tripDetails = this.tripRepository.findAll();


            return excelService.dataToExcel(tripDetails);

        } catch (Exception e) {

            logger.error("Error while generating Excel for trip details: {}", e.getMessage(), e);


            throw new RuntimeException("Error generating trip details Excel: " + e.getMessage(), e);
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @CachePut(cacheNames = "shortLivedCache", key = "#field")
    public DataResponse getTripBySorting(String filed) {
        List<TripDetails> tripDetailsList = this.tripRepository.findAll(Sort.by(Sort.Direction.ASC, filed));

        LinkedHashMap<String, Object> data = new LinkedHashMap<>();

        tripDetailsList.forEach(trip -> {
            TripDetailsDTO tripDTO = tripMapper.toDto(trip);
            data.put("Trip_" + trip.getId(), tripDTO);
        });

        return this.responseService.createResponse(
                data, "Based on " + filed + " Successfully trip details have been fetched!",
                HttpStatus.OK.value(), HttpStatus.OK, "1");

    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @CachePut(cacheNames = "shortLivedCache", key = "#offset + '-' + #pageSize")
    public Page<TripDetailsDTO> getTripsWithPagination(int offset, int pageSize) {

        Page<TripDetails> tripDetails=this.tripRepository.findAll(PageRequest.of(offset, pageSize));

        Page<TripDetailsDTO> tripDTOs=tripDetails.map(trip->this.tripMapper.toDto(trip));

        return  tripDTOs;
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Cacheable(cacheNames = "tripsCache", key = "#offset + '-' + #pageSize + '-' + #filed")
    public Page<TripDetailsDTO>getTripsWithPaginationWithSorting(int offset, int pageSize,String filed){
        Page<TripDetails> tripList=this.tripRepository.findAll(PageRequest.of(offset,pageSize, Sort.Direction.ASC,filed));

        return tripList.map(trip-> this.tripMapper.toDto(trip));
    }
}

