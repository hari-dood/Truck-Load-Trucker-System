package com.hariSolution.mapper;


import com.hariSolution.model.DataRequest;

import com.hariSolution.DTOs.TripDetailsDTO;
import org.springframework.stereotype.Component;


@Component
public class DataRequestMapper {

    public TripDetailsDTO toTripDto(DataRequest dataRequest) {
        TripDetailsDTO data = dataRequest.getData();

        if (data != null) {
            int totalExpenses = data.getBrokerAmount()
                    + data.getDownAmount()
                    + data.getUpAmount()
                    + data.getTollAmount()
                    + data.getOthersExpenses()
                    + data.getPcAmount()
                    + data.getRtoAmount()
                    + data.getWeightBridge();

            data.setTotalExpenses(totalExpenses);

        }
        return data;
    }
}
