package com.smart.service.mapper;

import com.smart.service.dtoResponse.ServiceResponse;
import com.smart.service.dtoResponse.ServicesImageDTO;
import com.smart.service.entity.ServiceEntity;
import com.smart.service.entity.ServicesImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface ServiceMapper {

    // Map Entity -> Response DTO
    // We specify "user.id" to get the Long value from the UserEntity
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "images", target = "imageUrl")
    ServiceResponse toResponse(ServiceEntity entity);

    default String mapImageEntityToString(ServicesImageEntity imageEntity) {
        if (imageEntity == null) return null;
        return imageEntity.getImageUrl();
    }
}