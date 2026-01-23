package com.smart.service.controller;

import com.smart.service.dtoRequest.ServiceRequest;
import com.smart.service.dtoResponse.ServiceResponse;
import com.smart.service.entity.ServiceEntity;
import com.smart.service.service.ServiceManagement;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceManagement serviceManagement;

    @PostMapping
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<ServiceResponse> create(@RequestBody ServiceRequest request, Principal principal) {
        ServiceResponse savedService = serviceManagement.createService(request, principal.getName());
        return new ResponseEntity<>(savedService, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<ServiceResponse> update(@PathVariable Long id, @RequestBody ServiceRequest request, Principal principal) {
        return ResponseEntity.ok(serviceManagement.updateService(id, request, principal.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceManagement.getServiceById(id));
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponse>> getAll() {
        return ResponseEntity.ok(serviceManagement.getAllServices());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<Void> delete(@PathVariable Long id, Principal principal) {
        serviceManagement.deleteService(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}
