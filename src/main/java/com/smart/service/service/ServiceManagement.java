package com.smart.service.service;

import com.smart.service.dtoRequest.ServiceRequest;
import com.smart.service.dtoResponse.ServiceResponse;
import com.smart.service.entity.ServiceEntity;
import com.smart.service.repository.ServiceRepository;

import java.util.List;

public interface ServiceManagement {
    ServiceResponse createService(ServiceRequest serviceRequest, String email);
    ServiceResponse updateService(Long id, ServiceRequest serviceRequest, String email);
    ServiceResponse getServiceById(Long id);
    List<ServiceResponse> getAllServices();
    void deleteService(Long id, String email);
}


