package com.smart.service.mapper;

import com.smart.service.dtoResponse.ProviderRequestResponse;
import com.smart.service.entity.ProviderRequestEntity;
import org.springframework.stereotype.Component;

@Component
public class ProviderRequestMapper {

    public ProviderRequestResponse toResponse(ProviderRequestEntity entity) {
        return ProviderRequestResponse.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .businessName(entity.getBusinessName())
                .businessBio(entity.getBusinessBio())
                .status(entity.getStatus().name())
                .adminNote(entity.getAdminNote())
                .createdAt(entity.getCreatedAt())
                .build();
    }





}
