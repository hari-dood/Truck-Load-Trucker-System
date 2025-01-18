package com.hariSolution.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;

@Data  // Lombok annotation for generating getters, setters, toString(), equals(), and hashCode() methods
@NoArgsConstructor  // Lombok annotation for generating a no-argument constructor
@AllArgsConstructor  // Lombok annotation for generating a constructor with arguments for all fields
@Entity  // Marks this class as a JPA entity that will be mapped to a database table
@SuperBuilder  // Lombok annotation for generating a builder pattern
@Table(name = "driver_info")  // Specifies the table name in the database
@Component  // Marks this class as a Spring component, making it eligible for dependency injection
@JsonSerialize  // Ensures the object can be serialized into JSON
@NamedQuery(  // Declares a named query to update driver information
        name = "DriverInfo.updateDriverInfoByIdNamedQuery",
        query = "UPDATE DriverInfo d SET d.firstName = :firstName, " +
                "d.fullName = :fullName, d.contactNumber = :contactNumber, " +
                "d.email = :email, d.experience = :experience, d.licenceNo = :licenceNo, d.aadhaarNo = :aadhaarNo " +
                "WHERE d.id = :id")
public class DriverInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id  // Marks the field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Indicates that the primary key is auto-generated
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 50)  // Specifies column properties
    private String firstName;

    @Column(name = "full_name", nullable = false, length = 100)  // Specifies column properties
    private String fullName;

    @Column(name = "contact_number", nullable = false, length = 15, unique = true)  // Specifies column properties
    private String contactNumber;

    @Column(name = "email", nullable = false, length = 50, unique = true)  // Specifies column properties
    private String email;

    @Column(name = "experience", nullable = false)  // Specifies column properties
    private String experience;

    @Column(name = "licence_no", nullable = false, unique = true, length = 20)  // Specifies column properties
    private String licenceNo;

    @Column(name = "aadhaar_card_no", nullable = false, unique = true, length = 16)  // Specifies column properties
    private String aadhaarNo;

    @Column(name = "address", nullable = false, length = 500)  // Specifies column properties
    private String address;
}
