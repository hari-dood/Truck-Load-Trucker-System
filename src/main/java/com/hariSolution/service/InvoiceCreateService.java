package com.hariSolution.service;

import com.hariSolution.DTOs.TripDetailsDTO;
import com.hariSolution.invoiceGenerator.InvoiceService;
import com.hariSolution.mapper.TripDetailsMapper;
import com.hariSolution.model.TripDetails;
import com.hariSolution.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

@Service
@RequiredArgsConstructor
public class InvoiceCreateService {
    private final TripRepository tripRepository;
    private final TripDetailsMapper tripDetailsMapper;
    private final InvoiceService invoiceService;

    public ByteArrayInputStream createInvoiceCreation(Integer trip_id) throws MalformedURLException, FileNotFoundException {
        TripDetails tripDetails = this.tripRepository.findById(trip_id).orElseThrow(() -> new RuntimeException("Trip not found"));
        TripDetailsDTO dto = this.tripDetailsMapper.toDto(tripDetails);

        return this.invoiceService.invoiceCreation(dto);
    }

}
