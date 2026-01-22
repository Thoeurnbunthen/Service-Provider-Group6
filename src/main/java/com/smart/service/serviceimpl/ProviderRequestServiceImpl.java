package com.smart.service.serviceimpl;

import com.smart.service.dtoResponse.ProviderRequestDto;
import com.smart.service.dtoResponse.ProviderRequestResponse;
import com.smart.service.entity.ProviderRequestEntity;
import com.smart.service.entity.RoleEntity;
import com.smart.service.entity.UserEntity;
import com.smart.service.enums.RequestStatus;
import com.smart.service.enums.enums;
import com.smart.service.exception.ResourceNotFoundException;
import com.smart.service.mapper.ProviderRequestMapper;
import com.smart.service.repository.ProviderRequestRepository;
import com.smart.service.repository.RoleRepository;
import com.smart.service.repository.UserRepository;
import com.smart.service.service.ProviderRequestService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProviderRequestServiceImpl implements ProviderRequestService {

    private final ProviderRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProviderRequestMapper mapper;

    @Override
    @Transactional
    public ProviderRequestResponse createRequest(ProviderRequestDto dto, UserEntity user) {
        if (requestRepository.existsByUser(user)) {
            throw new RuntimeException("Request already exists for this user.");
        }

        ProviderRequestEntity entity = ProviderRequestEntity.builder()
                .user(user)
                .businessName(dto.getBusinessName())
                .businessBio(dto.getBusinessBio())
                .status(RequestStatus.PENDING)
                .build();

        return mapper.toResponse(requestRepository.save(entity));
    }

    @Override
    public ProviderRequestResponse getMyRequestStatus(UserEntity user) {
        ProviderRequestEntity entity = requestRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("No request found."));
        return mapper.toResponse(entity);
    }

    @Override
    public List<ProviderRequestResponse> getAllPendingRequests() {
        return requestRepository.findAllByStatus(RequestStatus.PENDING).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void approveRequest(Long requestId) {
        ProviderRequestEntity request = requestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found"));

        //1 Update Request Status
        request.setStatus(RequestStatus.APPROVED);
        request.setAdminNote("Welcome! Your application has been verified.");
        requestRepository.save(request);

        //2 Assign Role to User
        UserEntity user = request.getUser();
        RoleEntity providerRole = roleRepository.findByName(enums.PROVIDER); //Assuming your enum is enums
                if(!providerRole.getName().equals(enums.PROVIDER)){
                    throw new ResourceNotFoundException("Provider not found");


                }
        user.getRoles().add(providerRole);
        userRepository.save(user);
    }

    @Override
    public void rejectRequest(Long requestId, String reason) {
      ProviderRequestEntity request = requestRepository.findById(requestId)
              .orElseThrow(() -> new ResourceNotFoundException("Request not found"));
        // 1. Update status to REJECTED
        request.setStatus(RequestStatus.REJECTED);
        // 2. Set the admin note so the user can see WHY they were rejected
        request.setAdminNote(reason);
        requestRepository.save(request);
        // Note: We do NOT add a role here. The user remains a regular USER.
    }
}
