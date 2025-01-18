package com.hariSolution.mapper;

import com.hariSolution.model.DriverInfo;
import com.hariSolution.DTOs.DriverInfoDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")  // Marks the interface as a MapStruct Mapper and enables Spring integration
public interface DriverMapper {

    // This provides a single instance of the DriverMapper interface
    DriverMapper INSTANCE = Mappers.getMapper(DriverMapper.class);

    // Method to convert DriverInfoDTO to DriverInfo entity
    public DriverInfo toEntity(DriverInfoDTO DriverDto);

    // Inverse method to convert DriverInfo entity back to DriverInfoDTO
    @InheritInverseConfiguration
    public DriverInfoDTO toDto(DriverInfo driverInfo);

    // Method to convert a list of DriverInfo entities to a list of DriverInfoDTOs
    public List<DriverInfoDTO> toDTOs(List<DriverInfo> driverInfo);
}
