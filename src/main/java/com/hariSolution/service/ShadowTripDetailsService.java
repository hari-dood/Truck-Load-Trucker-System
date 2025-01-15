package com.hariSolution.service;

import com.hariSolution.mapper.ShadowTripDetailsMapper;
import com.hariSolution.model.ShadowTripDetails;
import com.hariSolution.model.TripDetails;
import com.hariSolution.repository.ShadowTripRepository;
import com.hariSolution.repository.TripRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShadowTripDetailsService {

    private final TripRepository tripRepository;
    private final ShadowTripRepository shadowTripRepository;
    private final ShadowTripDetailsMapper shadowTripMapper;
    @Modifying
    @Transactional
    public ResponseEntity<Object> copyAllTripDetailsToTripDetails() {
        List<TripDetails> tripDetails = this.tripRepository.findAll();

        tripDetails.forEach(trip -> {
            ShadowTripDetails shadowTripDetails = shadowTripMapper.toTripDetails(trip);
            shadowTripRepository.save(shadowTripDetails);
        });

        return new ResponseEntity<>("Data copied to Shadow Class!", HttpStatus.OK);

    }
    @Modifying
    @Transactional
    public ResponseEntity<Object> copyTripDetailsToShadowDetails(TripDetails tripDetails) {
        ShadowTripDetails shadowTripDetails = shadowTripMapper.toTripDetails(tripDetails);
        shadowTripRepository.save(shadowTripDetails);

        return new ResponseEntity<>("Data copied to Shadow Class!", HttpStatus.OK);
    }


}
