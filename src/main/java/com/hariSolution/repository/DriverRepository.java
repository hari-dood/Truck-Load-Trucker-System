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

@Repository // Indicates that this is a Spring Data repository interface for managing DriverInfo entities.
public interface DriverRepository extends JpaRepository<DriverInfo, Integer> {
    // Custom query method to find all DriverInfo records by first name, ignoring case.
    // Spring Data JPA will automatically generate the query based on the method name.
    List<DriverInfo> findAllByFirstNameIgnoreCase(String firstName);

    // Custom query method to find all DriverInfo records with first names in a provided list, ignoring case.
    // Spring Data JPA will automatically generate the query based on the method name.
    List<DriverInfo> findAllByFirstNameInIgnoreCase(List<String> listOfFirstName);

    // Custom query using a named query to update DriverInfo fields by ID.
    @Modifying // Indicates that this is a modifying query (e.g., an UPDATE).
    @Transactional // Ensures that the update operation is wrapped in a transaction.
    @Query(name = "DriverInfo.updateDriverInfoByIdNamedQuery") // Refers to a named query defined in DriverInfo entity (could be JPQL or SQL).
    int updateDriverInfoByIdNamedQuery(
            @Param("id") Integer id, // The ID of the driver to update.
            @Param("firstName") String firstName, // The new first name of the driver.
            @Param("fullName") String fullName, // The new full name of the driver.
            @Param("contactNumber") String contactNumber, // The new contact number of the driver.
            @Param("email") String email, // The new email of the driver.
            @Param("experience") String experience, // The new experience of the driver.
            @Param("licenceNo") String licenceNo, // The new licence number of the driver.
            @Param("aadhaarNo") String aadhaarNo // The new Aadhaar number of the driver.
    );

    // Custom query method to find a DriverInfo record by its full name.
    DriverInfo findByFullName(String fullName); // Automatically generates a query to find the driver by their full name.

    /*
    Example of an alternative update method using JPQL (not used in the current code, but kept here as a comment).
    @Modifying
    @Transactional
    @Query("UPDATE DriverInfo d SET d.firstName = :firstName, d.fullName = :fullName, d.contactNumber = :contactNumber, d.email = :email WHERE d.id = :id")
    int updateDriverInfo(@Param("id") Long id,
                         @Param("firstName") String firstName,
                         @Param("fullName") String fullName,
                         @Param("contactNumber") String contactNumber,
                         @Param("email") String email);
    */
}
