package com.smart.service.repository;

import com.smart.service.entity.ProviderRequestEntity;
import com.smart.service.entity.UserEntity;
import com.smart.service.enums.RequestStatus;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProviderRequestRepository extends JpaRepository< ProviderRequestEntity, Long> {
    Optional<ProviderRequestEntity>findByUser(UserEntity user);
    boolean existsByUser(UserEntity user);
    List<ProviderRequestEntity> findAllByStatus(RequestStatus status);
}
