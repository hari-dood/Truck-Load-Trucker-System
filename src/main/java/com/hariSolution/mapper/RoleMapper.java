package com.hariSolution.mapper;

import com.hariSolution.DTOs.RoleInfoDTO;
import com.hariSolution.model.RoleInfo;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")  // Marks the interface as a MapStruct mapper and integrates with Spring
public interface RoleMapper {

    // Creates an instance of RoleMapper that will be automatically generated by MapStruct at compile-time
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    // Method to convert RoleInfoDTO to RoleInfo entity
    RoleInfo toEntity(RoleInfoDTO roleInfoDTO);

    // Method to convert RoleInfo entity to RoleInfoDTO
    @InheritInverseConfiguration  // Automatically uses the inverse of the toEntity method
    RoleInfoDTO toDTO(RoleInfo roleInfo);
}
