package com.hariSolution.repository;

import com.hariSolution.model.DriverInfo;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<DriverInfo, Integer> {
    //select * from author where first_name = 'hari'
    List<DriverInfo> findAllByFirstNameIgnoreCase(String firstName);

    List<DriverInfo> findAllByFirstNameInIgnoreCase(List<String> listOfFirstName);

    @Modifying
    @Transactional
    @Query(name = "DriverInfo.updateDriverInfoByIdNamedQuery")
    int updateDriverInfoByIdNamedQuery(
            @Param("id") Integer id,
            @Param("firstName") String firstName,
            @Param("fullName") String fullName,
            @Param("contactNumber") String contactNumber,
            @Param("email") String email,
            @Param("experience") String experience,
            @Param("licenceNo") String licenceNo,
            @Param("aadhaarNo") String aadhaarNo
    );

    DriverInfo findByFullName(String fullName);



/*
    @Modifying
    @Transactional
    @Query("UPDATE DriverInfo d SET d.firstName = :firstName, d.fullName = :fullName, d.contactNumber = :contactNumber, d.email = :email WHERE d.id = :id")
    int updateDriverInfo(@Param("id") Long id,
                         @Param("firstName") String firstName,
                         @Param("fullName") String fullName,
                         @Param("contactNumber") String contactNumber,
                         @Param("email") String email);*/
}
