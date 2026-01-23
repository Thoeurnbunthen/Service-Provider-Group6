package com.smart.service.serviceimpl;

import com.smart.service.dtoRequest.ServiceRequest;
import com.smart.service.dtoResponse.ServiceResponse;
import com.smart.service.entity.CategoryEntity;
import com.smart.service.entity.ServiceEntity;
import com.smart.service.entity.ServicesImageEntity;
import com.smart.service.entity.UserEntity;
import com.smart.service.exception.ResourceNotFoundException;
import com.smart.service.mapper.ServiceMapper;
import com.smart.service.repository.CategoryRepository;
import com.smart.service.repository.ServiceRepository;
import com.smart.service.repository.UserRepository;
import com.smart.service.service.ServiceManagement;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceManagementImpl implements ServiceManagement {

    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ServiceMapper serviceMapper;

    @Override
    public ServiceResponse createService(ServiceRequest serviceRequest, String email) {
        UserEntity provider = userRepository.findByEmail(email);
        CategoryEntity categoryEntity = categoryRepository.findById(serviceRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        // 1. Build the ServiceEntity (Same as before)
        ServiceEntity service = ServiceEntity.builder()
                .title(serviceRequest.getTitle())
                .price(serviceRequest.getPrice())
                .durationMinutes(serviceRequest.getDurationMinutes())
                .isActive(serviceRequest.getIsActive())
                .category(categoryEntity)
                .user(provider)
                .images(new ArrayList<>())
                .build();

        // 2. Add Images
        if (serviceRequest.getImageUrl() != null) {
            for (String url : serviceRequest.getImageUrl()) {
                ServicesImageEntity img = ServicesImageEntity.builder()
                        .imageUrl(url)
                        .service(service)
                        .build();
                service.getImages().add(img);
            }
        }

        // 3. Save the entity
        ServiceEntity savedService = serviceRepository.save(service);

        // 4. USE MAPSTRUCT HERE: Convert Entity to the Response DTO
        return serviceMapper.toResponse(savedService);
    }

    @Override
    public ServiceResponse updateService(Long id, ServiceRequest serviceRequest, String email) {
        ServiceEntity existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
        if (!existingService.getUser().getEmail().equals(email)) {
            throw new RuntimeException("You are not authorized to update this service");
        }
        //2.Update category if it changed
        CategoryEntity categoryEntity = categoryRepository.findById(serviceRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        //3.Update basic fields
        existingService.setTitle(serviceRequest.getTitle());
        existingService.setPrice(serviceRequest.getPrice());
        existingService.setDurationMinutes(serviceRequest.getDurationMinutes());
        existingService.setIsActive(serviceRequest.getIsActive());
        existingService.setCategory(categoryEntity);

        // 4. Update Images (Clear old and add new)
        existingService.getImages().clear();
        if (serviceRequest.getImageUrl() != null) {
            for (String url : serviceRequest.getImageUrl()) {
                ServicesImageEntity img = ServicesImageEntity.builder()
                        .imageUrl(url)
                        .service(existingService)
                        .build();
                existingService.getImages().add(img);
            }
        }
        return serviceMapper.toResponse(serviceRepository.save(existingService));
    }

    @Override
    public ServiceResponse getServiceById(Long id) {
        return serviceRepository.findById(id)
                .map(serviceMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
    }

    @Override
    public List<ServiceResponse> getAllServices() {
        return serviceRepository.findAll().stream()
                .map(serviceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteService(Long id, String email) {
        ServiceEntity existingService = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found"));
        if (!existingService.getUser().getEmail().equals(email)){
            throw new RuntimeException("You are not authorized to delete this service");
        }
    serviceRepository.delete(existingService);
    }
}
