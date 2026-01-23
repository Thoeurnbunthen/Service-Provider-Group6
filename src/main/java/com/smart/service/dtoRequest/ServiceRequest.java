package com.smart.service.dtoRequest;

import lombok.Data;

import java.util.List;

@Data
public class ServiceRequest {

    private String title;
    private Double price;
    private Integer durationMinutes;
    private Long categoryId;
    private Boolean isActive;
    private List<String> imageUrl;
}
