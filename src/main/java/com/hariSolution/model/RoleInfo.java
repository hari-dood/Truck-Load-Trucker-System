package com.hariSolution.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity  // Marks the class as a JPA entity
@NoArgsConstructor  // Lombok annotation to generate a no-argument constructor
@AllArgsConstructor  // Lombok annotation to generate a constructor with all arguments
@Data  // Lombok annotation to generate getters, setters, toString(), equals(), and hashCode() methods
@JsonSerialize  // Ensures that the object can be serialized into JSON
public class RoleInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;  // Serial version UID for serialization

    @Id  // Marks the field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Specifies that the primary key is auto-generated
    private Integer id;  // The ID of the role

    @Column(nullable = false, unique = true)  // Maps the field to a column in the database
    private String name;  // The name of the role (e.g., "Admin", "User")

    @Column(nullable = true)  // Maps the field to a column in the database
    private String roleDescription;  // A description of the role (optional)
}
