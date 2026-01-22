package com.smart.service.controller;

import com.smart.service.dtoResponse.ProviderRequestResponse;
import com.smart.service.service.ProviderRequestService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/requests")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminRequestController {

    private final ProviderRequestService requestService;

    @GetMapping
    public ResponseEntity<@NonNull List<ProviderRequestResponse>> getPending() {
        return ResponseEntity.ok(requestService.getAllPendingRequests());
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<@NonNull String> approve(@PathVariable Long id) {
        requestService.approveRequest(id);
        return ResponseEntity.ok("Provider approved successfully.");
    }
    @PatchMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<@NonNull String> rejectUser(@PathVariable Long id,
    @RequestParam String reason
        ){
        requestService.rejectRequest(id, reason);
        return ResponseEntity.ok("Provider request rejected with reason: " + reason);


    }

}
