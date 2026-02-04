package com.smart.service.service;

import com.smart.service.dtoRequest.AvailabilityRequest;
import com.smart.service.dtoResponse.AvailabilityResponse;
import com.smart.service.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AvailabilityService {
    // Provider: Add a new time slot
    AvailabilityResponse addAvailability(AvailabilityRequest dto, UserEntity provider);

    // Customer: See what slots a provider has open
    List<AvailabilityResponse> getProviderAvailability(Long providerId);

    // Provider: Delete a slot (only if not booked)
    void deleteAvailability(Long availabilityId, UserEntity provider);
}
