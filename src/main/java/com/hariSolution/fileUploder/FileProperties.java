package com.hariSolution.fileUploder;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

// @Entity annotation indicates that this class is a JPA entity, and it will be mapped to a database table.
// The class represents a record in the "attachment_information" table.
@Entity
@Data // Lombok annotation to generate getter, setter, equals, hashcode, toString methods.
@Table(name = "attachment_information") // Specifies the name of the table in the database.
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor.
@AllArgsConstructor // Lombok annotation to generate an all-argument constructor.
@JsonSerialize // Jackson annotation to indicate that instances of this class should be serialized to JSON.
public class FileProperties implements Serializable {

    // The serialVersionUID is used for serialization and deserialization of this class.
    @Serial
    private static final long serialVersionUID = 1L;

    // The @Id annotation indicates the primary key of the entity.
    @Id
    // @GeneratedValue strategy specifies that the id will be generated automatically by the database.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The fileName field stores the name of the uploaded file.
    private String fileName;

    // The fileType field stores the type (or MIME type) of the uploaded file (e.g., "image/png", "application/pdf").
    private String fileType;

    // The @Lob annotation indicates that the data field will store large binary data (like file content).
    @Lob
    // The data field will hold the actual file content as a byte array.
    private byte[] data;
}
