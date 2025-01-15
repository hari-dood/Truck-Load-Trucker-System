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


@Data
@RequiredArgsConstructor
@Table(name = "Http_request_and_response_log_information")
@ToString
@Entity
@JsonSerialize
public class LogInfo  implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private long id;

    @Column(name = "ENDPOINT")
    private String uri;

    @Column(name = "HTTP_METHOD")
    private String httpMethod;

    @Column(name = "REQUEST")
    private String request;

    @Column(name = "RESPONSE",length = 100000)
    private String response;

    @CreationTimestamp
    @Column(name="TIMESTAMP_REQ_RES")
    private String createdDate;
}
