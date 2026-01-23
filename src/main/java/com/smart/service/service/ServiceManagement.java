package com.smart.service.service;

import com.smart.service.dtoRequest.ServiceRequest;
import com.smart.service.dtoResponse.ServiceResponse;
import com.smart.service.entity.ServiceEntity;

public interface ServiceManagement {
    ServiceResponse createService (ServiceRequest serviceRequest, String email);

}
