package com.hariSolution.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hariSolution.DTOs.TripDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@JsonSerialize
public class DataRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private TripDetailsDTO data;

    private String _request_id;
    private String _client_ts;
    private String _client_type;
}
