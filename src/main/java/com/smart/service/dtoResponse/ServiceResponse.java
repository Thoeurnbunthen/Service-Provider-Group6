package com.smart.service.dtoResponse;


import com.smart.service.entity.CategoryEntity;
import com.smart.service.entity.ServicesImageEntity;
import com.smart.service.entity.UserEntity;
import lombok.Data;

import java.util.List;
@Data
public class ServiceResponse {

    private Long id;
    private String title;
    private Double price;
    private Integer durationMinutes;
    private Boolean isActive;
    private Long userId;
    private CategoryDTO category;
    private List<String> imageUrl;
}
