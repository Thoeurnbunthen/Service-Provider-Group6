package com.smart.service.mapper;
import com.smart.service.dtoResponse.ProviderRequestResponse;
import com.smart.service.entity.ProviderRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProviderRequestMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "status", target = "status")
    ProviderRequestResponse toResponse (ProviderRequestEntity entity);
    // Enum -> String conversion

    default String mapStatusToString(Enum<?> status) {
        return status != null ? status.name() : null;
    }
}
