package com.hariSolution.mapper;

import com.hariSolution.DTOs.RoleInfoDTO;
import com.hariSolution.model.RoleInfo;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    // Convert RoleInfoDTO to RoleInfo entity
    RoleInfo toEntity(RoleInfoDTO roleInfoDTO);

    // Convert RoleInfo entity to RoleInfoDTO
    @InheritInverseConfiguration
    RoleInfoDTO toDTO(RoleInfo roleInfo);

}
