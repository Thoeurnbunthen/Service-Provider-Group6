package com.smart.service.controller;

import com.smart.service.dtoResponse.ProviderRequestDto;
import com.smart.service.dtoResponse.ProviderRequestResponse;
import com.smart.service.entity.UserEntity;
import com.smart.service.service.ProviderRequestService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/provider-requests")
@RequiredArgsConstructor
public class ProviderRequestController {
    private final ProviderRequestService requestService;
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<@NonNull ProviderRequestResponse> submitRequest(
            @RequestBody ProviderRequestDto dto,
            @AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(requestService.createRequest(dto, user));
    }
    @GetMapping("/status")
    @PreAuthorize("hasRole( 'USER')")
    public ResponseEntity<@NonNull ProviderRequestResponse> getStatus(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(requestService.getMyRequestStatus(user));
    }
}
