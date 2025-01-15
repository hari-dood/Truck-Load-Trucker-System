package com.hariSolution.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class DataResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String,Object> data;
    private Map<String,Object> status_details;

}
