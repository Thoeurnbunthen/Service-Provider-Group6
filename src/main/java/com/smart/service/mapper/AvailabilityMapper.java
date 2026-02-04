package com.smart.service.mapper;

import com.smart.service.dtoRequest.AvailabilityRequest;
import com.smart.service.dtoResponse.AvailabilityResponse;
import com.smart.service.entity.AvailabilityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AvailabilityMapper {

    AvailabilityResponse toResponse(AvailabilityEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "provider", ignore = true)
    // Change "booked" to "isBooked" to match your Entity field name exactly
    @Mapping(target = "isBooked", constant = "false")
    AvailabilityEntity toEntity(AvailabilityRequest dto);
}
