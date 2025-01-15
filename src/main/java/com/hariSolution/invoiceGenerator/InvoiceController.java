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

@RestController
@RequestMapping("/api/v1/invoice")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceCreateService invoiceCreateService;

    @GetMapping("/{trip-id}")
    public ResponseEntity<InputStreamResource> createInvoice(@PathVariable("trip-id") Integer trip_id) throws MalformedURLException, FileNotFoundException {

        // Generate the PDF invoice as a ByteArrayInputStream
        ByteArrayInputStream pdfStream = this.invoiceCreateService.createInvoiceCreation(trip_id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=invoice_trip_" + trip_id + "_" + LocalDateTime.now() + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }
}




