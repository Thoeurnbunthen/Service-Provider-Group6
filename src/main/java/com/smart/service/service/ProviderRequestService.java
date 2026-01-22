package com.smart.service.service;

import com.smart.service.dtoResponse.ProviderRequestDto;
import com.smart.service.dtoResponse.ProviderRequestResponse;
import com.smart.service.entity.UserEntity;

import java.util.List;

public interface ProviderRequestService {
    ProviderRequestResponse createRequest(ProviderRequestDto dto, UserEntity user);
    ProviderRequestResponse getMyRequestStatus(UserEntity user);
    List<ProviderRequestResponse> getAllPendingRequests();
    void approveRequest(Long requestId);
    void rejectRequest (Long requestId, String reason);
}
