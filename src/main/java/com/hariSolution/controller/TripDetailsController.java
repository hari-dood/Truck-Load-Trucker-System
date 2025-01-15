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

    private final TripDetailsService tripDetailsService;
    private final TripRepository tripRepository;
    private final PdfGeneratorService pdfService;

/*    @Value("${excel.directory.path}")
    private String directoryPath;*/

    @PostMapping("/createTrip")
    public ResponseEntity<DataResponse> createTripInformation(@RequestBody @Valid DataRequest dataRequest) {

        return this.tripDetailsService.createTripInformation(dataRequest);
    }

    @GetMapping("/get-allTrips")
    public ResponseEntity<DataResponse> getAllTripInformation() {
        // This method will delegate the actual logic to the service
        DataResponse response = this.tripDetailsService.getAllTripInformation();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/get-trips/{driverName}")
    public ResponseEntity<DataResponse> getTripInformationByDriverName(@PathVariable("driverName") String driverName) {
        // Delegate the logic to the service layer. If any exception occurs, it will be handled by the global exception handler.
        DataResponse response = this.tripDetailsService.getTripInformationByDriverInfo(driverName);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/admin/trips-in/{month}")
    public ResponseEntity<DataResponse> getTripInformationMonth(@PathVariable("month") String month) {
        // Delegate the logic to the service layer. The global exception handler will catch any exceptions.
        DataResponse response = this.tripDetailsService.getTripInformationMonth(month);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/trips-on/{loadingPoint}")
    public ResponseEntity<DataResponse> getTripInformationLoadingPoint(@PathVariable("loadingPoint") String loadingPoint) {
        // Delegate the logic to the service layer. The global exception handler will catch any exceptions.
        DataResponse response = this.tripDetailsService.getTripInformationLoadingPoint(loadingPoint);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/greater_than_or_equal_to/loadAmount")
    public ResponseEntity<DataResponse> getTripInformationLoadAmount(@RequestParam("loadAmount") Integer loadAmount) {
        // Delegate the logic to the service layer. The global exception handler will catch any exceptions.
        DataResponse response = this.tripDetailsService.getTripInformationLoadAmount(loadAmount);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/update-tripDetails/{id}")
    public ResponseEntity<DataResponse> updatedTripDetails(@PathVariable("id") Integer trip_id, @RequestBody DataRequest request) {
        // Delegate the logic to the service layer. The global exception handler will catch any exceptions.
        DataResponse response = this.tripDetailsService.updateTripDetails(trip_id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-tripDetails/{id}")
    public ResponseEntity<DataResponse> deleteTripDetails(@PathVariable("id") Integer id) {
        // Delegate the logic to the service layer. The global exception handler will catch any exceptions.
        DataResponse response = this.tripDetailsService.deleteTripDetails(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/download/xl")
    public ResponseEntity<InputStreamResource> downloadAllTripInformationExcel() throws IOException {
        String fileName = "Trip_details_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".xlsx";
        // FileOutputStream fileOut = new FileOutputStream(directoryPath + "/" + fileName);
        // Generate the Excel file
        ByteArrayInputStream inputStream = this.tripDetailsService.downloadAllTripInformationExcel();

        // Create InputStreamResource for the response
        InputStreamResource resource = new InputStreamResource(inputStream);

        // Return the response entity
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


    @GetMapping("/download/pdf")
    public ResponseEntity<InputStreamResource> generateTripsInformationPdfReport() {
        List<TripDetails> tripDetails = tripRepository.findAll();

        if (tripDetails.isEmpty()) {
            throw new RuntimeException("No trip data available to generate the report.");
        }

        ByteArrayInputStream pdfData = pdfService.DataToPdf(tripDetails);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=trips_information_report_" + LocalDateTime.now() + ".pdf");

        /*String timestamp = LocalDateTime.now().toString().replace(":", "-"); // Replace invalid characters
        String filename = "trips_information_report_" + timestamp + ".pdf";
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);*/
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfData));
    }

    @GetMapping("/filter/{filed}")
    public ResponseEntity<DataResponse> getTripBySorting(@PathVariable("filed") String filed) {

        DataResponse response = this.tripDetailsService.getTripBySorting(filed);

        return ResponseEntity.ok(response);

    }

    @GetMapping("pagination/{offset}/{pageSize}")
    public Page<TripDetailsDTO> getTripsWithPagination(@PathVariable("offset") int offset, @PathVariable("pageSize") int pageSize) {

        return this.tripDetailsService.getTripsWithPagination(offset, pageSize);
    }

    @GetMapping("pagination-sorting/{offset}/{pageSize}/{filed}")
    public Page<TripDetailsDTO>getTripsWithPaginationWithSorting(@PathVariable("offset")int offset,@PathVariable("pageSize") int pageSize,@PathVariable("filed")String filed) {

        return this.tripDetailsService.getTripsWithPaginationWithSorting(offset, pageSize,filed);
    }


}
