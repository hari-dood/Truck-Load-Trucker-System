package com.hariSolution.mapper;

import com.hariSolution.model.DataResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class DataResponseMapper {

    public DataResponse createResponse(Map<String, Object> data, String status, int statusCode, HttpStatus statusMessage,
                                       String requestId) {

        DataResponse response = new DataResponse();

        Map<String, Object> statusDetails = new HashMap<>();

        statusDetails.put("status", status);
        statusDetails.put("statusCode", statusCode);
        statusDetails.put("status_message", statusMessage);
        statusDetails.put("requestId", requestId);
        statusDetails.put("_server_timestamp", Instant.now().toString());
        response.setStatus_details(statusDetails);

        response.setData(data);
        return response;

    }

}
