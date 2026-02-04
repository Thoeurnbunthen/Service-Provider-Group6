package com.smart.service.repository;

import com.smart.service.entity.AvailabilityEntity;
import com.smart.service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<AvailabilityEntity, Long> {

    // Find slots for a specific provider that aren't booked yet
    List<AvailabilityEntity> findByProviderIdAndIsBookedFalse(Long providerId);

    // Check if a provider already set this exact slot (to prevent duplicates)
    boolean existsByProviderAndAvailableDateAndStartTime(
            UserEntity provider, LocalDate date, LocalTime start
    );
}
