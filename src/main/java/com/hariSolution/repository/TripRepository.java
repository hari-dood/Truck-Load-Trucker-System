package com.hariSolution.repository;

import com.hariSolution.model.DriverInfo;
import com.hariSolution.model.TripDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<TripDetails,Integer> {

    List<TripDetails> findAllByDriverNameIgnoreCase(String driverName);

    List<TripDetails> findAllByMonthIgnoreCase(String month);

    List<TripDetails> findAllByLoadingPointIgnoreCase(String loadingPoint);

    List<TripDetails> findByLoadAmountNamedQuery(@Param("loadAmount") Integer loadAmount);
}
