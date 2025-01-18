package com.hariSolution.mapper;

import com.hariSolution.model.DataRequest;
import com.hariSolution.DTOs.TripDetailsDTO;
import org.springframework.stereotype.Component;

@Component  // Marks this class as a Spring component, making it eligible for autowiring
public class DataRequestMapper {

    // Method to map DataRequest object to TripDetailsDTO
    public TripDetailsDTO toTripDto(DataRequest dataRequest) {
        // Get the 'data' object from the DataRequest
        TripDetailsDTO data = dataRequest.getData();

        // Check if the data is not null
        if (data != null) {
            // Calculate the total expenses by summing up various individual amounts
            int totalExpenses = data.getBrokerAmount()
                    + data.getDownAmount()
                    + data.getUpAmount()
                    + data.getTollAmount()
                    + data.getOthersExpenses()
                    + data.getPcAmount()
                    + data.getRtoAmount()
                    + data.getWeightBridge();

            // Set the calculated total expenses in the TripDetailsDTO object
            data.setTotalExpenses(totalExpenses);
        }

        // Return the populated TripDetailsDTO object
        return data;
    }
}
