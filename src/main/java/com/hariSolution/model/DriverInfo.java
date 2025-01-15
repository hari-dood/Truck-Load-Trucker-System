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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "driver_info")
@Component
@JsonSerialize
@NamedQuery(
        name = "DriverInfo.updateDriverInfoByIdNamedQuery",
        query = "UPDATE DriverInfo d SET d.firstName = :firstName, " +
                "d.fullName = :fullName, d.contactNumber = :contactNumber, " +
                "d.email = :email, d.experience = :experience, " +
                "d.licenceNo = :licenceNo, d.aadhaarNo = :aadhaarNo " +
                "WHERE d.id = :id")
public class DriverInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    //@Query("update DriverInfo a set a.firstName= :firstname or b set b.fullname=
    // :firstname or  c set c.contactNumber= :contactNumber or e set e.email= :email where a.id = :id")

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "contact_number", nullable = false, length = 15, unique = true)
    private String contactNumber;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "experience", nullable = false)
    private String experience;

    @Column(name = "licence_no", nullable = false, unique = true, length = 20)
    private String licenceNo;

    @Column(name = "aadhaar_card_no", nullable = false, unique = true, length = 16)
    private String aadhaarNo;

    @Column(name = "address", nullable = false, length = 500)
    private String address;

}
