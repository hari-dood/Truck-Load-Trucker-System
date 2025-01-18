package com.hariSolution.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serial;
import java.io.Serializable;

@Data  // Lombok annotation for generating getters, setters, toString(), equals(), and hashCode() methods
@RequiredArgsConstructor  // Lombok annotation for generating a constructor with required arguments
@Table(name = "Http_request_and_response_log_information")  // Specifies the table name in the database
@ToString  // Lombok annotation for generating a toString method
@Entity  // Marks this class as a JPA entity
@JsonSerialize  // Ensures the object can be serialized into JSON
public class LogInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;  // Serial version UID for serialization

    @Id  // Marks the field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Specifies that the primary key is auto-generated
    @Column(name = "ID")  // Specifies the column name for this field
    private long id;

    @Column(name = "ENDPOINT")  // Specifies the column name for this field
    private String uri;  // The URI of the HTTP request

    @Column(name = "HTTP_METHOD")  // Specifies the column name for this field
    private String httpMethod;  // The HTTP method (GET, POST, PUT, DELETE, etc.)

    @Column(name = "REQUEST")  // Specifies the column name for this field
    private String request;  // The body of the HTTP request

    @Column(name = "RESPONSE", length = 100000)  // Specifies the column name and max length for this field
    private String response;  // The body of the HTTP response

    @CreationTimestamp  // Automatically sets the timestamp when the entity is created
    @Column(name = "TIMESTAMP_REQ_RES")  // Specifies the column name for this field
    private String createdDate;  // The timestamp of when the request and response were logged
}
