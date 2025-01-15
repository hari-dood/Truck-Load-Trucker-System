package com.hariSolution.mapper;

import com.hariSolution.model.ShadowTripDetails;
import com.hariSolution.model.TripDetails;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface ShadowTripDetailsMapper {
    ShadowTripDetailsMapper INSTANCE= Mappers.getMapper(ShadowTripDetailsMapper.class);

    public ShadowTripDetails toTripDetails(TripDetails tripDetails);
}
