package com.smart.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "services")
@Builder
@NoArgsConstructor // REQUIRED for JPA
@AllArgsConstructor // REQUIRED for Builder
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Double price;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "is_active")
    private Boolean isActive = true;

    // Relationship with Category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    // Relationship with User (The provider/owner)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    // One Service can have many images
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)// CascadeType means in the table if we delete service, the images will delete too.
    private List<ServicesImageEntity> images = new ArrayList<>();

}