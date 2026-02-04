package com.smart.service.controller;


import com.smart.service.dtoRequest.AvailabilityRequest;
import com.smart.service.dtoResponse.AvailabilityResponse;
import com.smart.service.entity.UserEntity;
import com.smart.service.service.AvailabilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AvailabilityController {
    private final AvailabilityService availabilityService;

    /**
     * PROVIDER: Add a new time slot to their schedule.
     */
    @PostMapping("/provider/availability")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<AvailabilityResponse> addAvailability(
            @Valid @RequestBody AvailabilityRequest request,
            @AuthenticationPrincipal UserEntity currentUser) {

        AvailabilityResponse response = availabilityService.addAvailability(request, currentUser);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * ANYONE: View all available (unbooked) slots for a specific provider.
     * Used by customers before booking.
     */
    @GetMapping("/providers/{providerId}/availability")
    public ResponseEntity<List<AvailabilityResponse>> getProviderAvailability(
            @PathVariable Long providerId) {

        List<AvailabilityResponse> response = availabilityService.getProviderAvailability(providerId);
        return ResponseEntity.ok(response);
    }

    /**
     * PROVIDER: Remove a time slot from their schedule.
     */
    @DeleteMapping("/provider/availability/{id}")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<Void> deleteAvailability(
            @PathVariable Long id,
            @AuthenticationPrincipal UserEntity currentUser) {

        availabilityService.deleteAvailability(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}
