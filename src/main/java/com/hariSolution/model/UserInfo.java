package com.hariSolution.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)  // Ensures equality check takes superclass fields into account
@NoArgsConstructor // Generates a no-argument constructor
@AllArgsConstructor // Generates a constructor with all arguments
@Entity  // Marks the class as a JPA entity
@Data  // Lombok annotation to generate getters, setters, toString(), equals(), and hashCode() methods
@JsonSerialize  // Annotates the class for JSON serialization
@SuperBuilder  // Lombok annotation to generate a builder pattern constructor
public class UserInfo extends AuditEntity implements Serializable {

    private static final long SERIAL = 1L;

    @Id  // Marks the field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Specifies that the primary key is auto-generated
    private Integer id;

    // Username for the user; unique and cannot be null
    @Column(nullable = false, unique = true)
    private String username;

    // Password for the user; cannot be null
    @Column(nullable = false)
    private String password;

    // Email for the user; unique and cannot be null
    @Column(nullable = false, unique = true)
    private String email;

    // Many-to-many relationship between UserInfo and RoleInfo entities
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_roles",  // Name of the junction table for the many-to-many relationship
            joinColumns = @JoinColumn(name = "user_id"),  // Foreign key to the UserInfo entity
            inverseJoinColumns = @JoinColumn(name = "role_id")  // Foreign key to the RoleInfo entity
    )
    private List<RoleInfo> roles = new ArrayList<>();  // List of roles associated with the user

}
