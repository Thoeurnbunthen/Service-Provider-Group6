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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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
}
