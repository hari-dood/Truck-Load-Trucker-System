package com.hariSolution.mapper;

import com.hariSolution.model.DriverInfo;
import com.hariSolution.model.TripDetails;
import com.hariSolution.DTOs.TripDetailsDTO;
import com.hariSolution.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class TripDetailsMapper {

    private final DriverRepository driverRepository;

    public TripDetails toEntity(TripDetailsDTO tripDto) {
        TripDetails tripDetails = new TripDetails();

        // Direct mappings from DTO to Entity
        tripDetails.setMonth(tripDto.getMonth());
        tripDetails.setLoadingDate(tripDto.getLoadingDate());
        tripDetails.setTripStartDate(tripDto.getTripStartDate());
        tripDetails.setTripEndDate(tripDto.getTripEndDate());
        tripDetails.setPaymentStatus(tripDto.getPaymentStatus());
        tripDetails.setLoadingPoint(tripDto.getLoadingPoint());
        tripDetails.setDeliveryPoint(tripDto.getDeliveryPoint());
        tripDetails.setLoadQuantity(tripDto.getLoadQuantity());
        tripDetails.setGoodsDescription(tripDto.getGoodsDescription());
        tripDetails.setLoadAmount(tripDto.getLoadAmount());
        tripDetails.setLoadAdvance(tripDto.getLoadAdvance());
        tripDetails.setWeightBridge(tripDto.getWeightBridge());
        tripDetails.setBrokerAmount(tripDto.getBrokerAmount());
        tripDetails.setUpAmount(tripDto.getUpAmount());
        tripDetails.setDownAmount(tripDto.getDownAmount());
        tripDetails.setTollAmount(tripDto.getTollAmount());
        tripDetails.setDieselExpenses(tripDto.getDieselExpenses());
        tripDetails.setRtoAmount(tripDto.getRtoAmount());
        tripDetails.setPcAmount(tripDto.getPcAmount());
        tripDetails.setOthersExpenses(tripDto.getOthersExpenses());
        tripDetails.setClientReqId(tripDto.getClientReqId());

        // Calculate and set total expenses with null-safe checks
        int totalExpenses = (tripDto.getBrokerAmount() != null ? tripDto.getBrokerAmount() : 0)
                + (tripDto.getDieselExpenses() != null ? tripDto.getDieselExpenses() : 0)
                + (tripDto.getDownAmount() != null ? tripDto.getDownAmount() : 0)
                + (tripDto.getUpAmount() != null ? tripDto.getUpAmount() : 0)
                + (tripDto.getTollAmount() != null ? tripDto.getTollAmount() : 0)
                + (tripDto.getOthersExpenses() != null ? tripDto.getOthersExpenses() : 0)
                + (tripDto.getPcAmount() != null ? tripDto.getPcAmount() : 0)
                + (tripDto.getRtoAmount() != null ? tripDto.getRtoAmount() : 0)
                + (tripDto.getWeightBridge() != null ? tripDto.getWeightBridge() : 0);
        tripDetails.setTotalExpenses(totalExpenses);

        // Set driver information if driver name is provided
        if (tripDto.getDriverName() != null && !tripDto.getDriverName().isEmpty()) {
            DriverInfo driverInfo = driverRepository.findByFullName(tripDto.getDriverName());
            if (driverInfo != null) {
                tripDetails.setDriverInfo(driverInfo);
                tripDetails.setDriverName(driverInfo.getFullName());
            } else {
                tripDetails.setDriverName(tripDto.getDriverName());
            }
        }

        return tripDetails;
    }


    public TripDetailsDTO toDto(TripDetails tripDetails) {
        TripDetailsDTO tripDto = new TripDetailsDTO();

        // Direct mappings from Entity to DTO
        tripDto.setMonth(tripDetails.getMonth());
        tripDto.setLoadingDate(tripDetails.getLoadingDate());
        tripDto.setTripStartDate(tripDetails.getTripStartDate());
        tripDto.setTripEndDate(tripDetails.getTripEndDate());
        tripDto.setPaymentStatus(tripDetails.getPaymentStatus());
        tripDto.setLoadingPoint(tripDetails.getLoadingPoint());
        tripDto.setDeliveryPoint(tripDetails.getDeliveryPoint());
        tripDto.setLoadQuantity(tripDetails.getLoadQuantity());
        tripDto.setGoodsDescription(tripDetails.getGoodsDescription());
        tripDto.setLoadAmount(tripDetails.getLoadAmount());
        tripDto.setLoadAdvance(tripDetails.getLoadAdvance());
        tripDto.setWeightBridge(tripDetails.getWeightBridge());
        tripDto.setBrokerAmount(tripDetails.getBrokerAmount());
        tripDto.setUpAmount(tripDetails.getUpAmount());
        tripDto.setDownAmount(tripDetails.getDownAmount());
        tripDto.setTollAmount(tripDetails.getTollAmount());
        tripDto.setDieselExpenses(tripDetails.getDieselExpenses());
        tripDto.setRtoAmount(tripDetails.getRtoAmount());
        tripDto.setPcAmount(tripDetails.getPcAmount());
        tripDto.setOthersExpenses(tripDetails.getOthersExpenses());
        tripDto.setClientReqId(tripDetails.getClientReqId());

        // Calculate total expenses with null safety
        int totalExpenses = (tripDetails.getBrokerAmount() != null ? tripDetails.getBrokerAmount() : 0)
                + (tripDetails.getUpAmount() != null ? tripDetails.getUpAmount() : 0)
                + (tripDetails.getDownAmount() != null ? tripDetails.getDownAmount() : 0)
                + (tripDetails.getTollAmount() != null ? tripDetails.getTollAmount() : 0)
                + (tripDetails.getDieselExpenses() != null ? tripDetails.getDieselExpenses() : 0)
                + (tripDetails.getRtoAmount() != null ? tripDetails.getRtoAmount() : 0)
                + (tripDetails.getPcAmount() != null ? tripDetails.getPcAmount() : 0)
                + (tripDetails.getOthersExpenses() != null ? tripDetails.getOthersExpenses() : 0)
                + (tripDetails.getWeightBridge() != null ? tripDetails.getWeightBridge() : 0);

        tripDto.setTotalExpenses(totalExpenses);

        // Set driver name if DriverInfo is available
        tripDto.setDriverName(tripDetails.getDriverInfo() != null ? tripDetails.getDriverInfo().getFullName() : null);

        return tripDto;
    }


}
