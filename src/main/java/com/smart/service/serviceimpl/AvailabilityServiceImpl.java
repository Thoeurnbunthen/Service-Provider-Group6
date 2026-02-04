package com.smart.service.serviceimpl;

import com.smart.service.dtoRequest.AvailabilityRequest;
import com.smart.service.dtoResponse.AvailabilityResponse;
import com.smart.service.entity.AvailabilityEntity;
import com.smart.service.entity.UserEntity;
import com.smart.service.mapper.AvailabilityMapper;
import com.smart.service.repository.AvailabilityRepository;
import com.smart.service.service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final AvailabilityMapper availabilityMapper;

    @Override
    public AvailabilityResponse addAvailability(AvailabilityRequest dto, UserEntity provider) {
        // 1. Business Validation: Prevent overlapping or duplicate slots for this provider
        boolean alreadyExists = availabilityRepository.existsByProviderAndAvailableDateAndStartTime(
                provider, dto.getAvailableDate(), dto.getStartTime());

        if (alreadyExists) {
            throw new RuntimeException("You have already listed this time slot as available.");
        }

        // 2. Map DTO to Entity (MapStruct handles booked=false and id=null)
        AvailabilityEntity entity = availabilityMapper.toEntity(dto);

        // 3. Manually link the provider (since we ignored it in MapStruct)
        entity.setProvider(provider);

        // 4. Save and return the mapped response
        AvailabilityEntity savedEntity = availabilityRepository.save(entity);
        return availabilityMapper.toResponse(savedEntity);

    }

    @Override
    public List<AvailabilityResponse> getProviderAvailability(Long providerId) {
        // Fetch slots that belong to the provider and aren't booked yet
        return availabilityRepository.findByProviderIdAndIsBookedFalse(providerId)
                .stream()
                .map(availabilityMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAvailability(Long availabilityId, UserEntity provider) {
        AvailabilityEntity availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new RuntimeException("Availability slot not found"));

        // Security check: Ensure the logged-in provider owns this slot
        if (!availability.getProvider().getId().equals(provider.getId())) {
            throw new RuntimeException("You are not authorized to delete this slot.");
        }

        // Business rule: Cannot delete if a customer already booked it
        if (availability.isBooked()) {
            throw new RuntimeException("Cannot delete a slot that has an active booking.");
        }

        availabilityRepository.delete(availability);

    }
}
