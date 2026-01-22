package com.smart.service.dtoResponse;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProviderRequestResponse {

    private Long id;
    private Long userId;
    private String businessName;
    private String businessBio;
    private String status;
    private String adminNote;
    private LocalDateTime createdAt;
}
