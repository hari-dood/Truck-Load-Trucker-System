package com.hariSolution.repository;

import com.hariSolution.model.ShadowTripDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShadowTripRepository extends JpaRepository<ShadowTripDetails,Integer> {

}
