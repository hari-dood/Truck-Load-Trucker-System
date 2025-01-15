package com.hariSolution.mapper;

import com.hariSolution.model.DriverInfo;
import com.hariSolution.DTOs.DriverInfoDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DriverMapper {
    DriverMapper INSTANCE= Mappers.getMapper(DriverMapper.class);

    public DriverInfo toEntity(DriverInfoDTO DriverDto);

    @InheritInverseConfiguration
    public DriverInfoDTO toDto(DriverInfo driverInfo);

    public List<DriverInfoDTO> toDTOs(List<DriverInfo> driverInfo);
}
