package com.smart.service.serviceimpl;

import com.smart.service.dtoRequest.CategoryRequest;
import com.smart.service.entity.CategoryEntity;
import com.smart.service.repository.CategoryRepository;
import com.smart.service.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryService> getAllCategories() {
        return List.of();
    }

    @Override
    public CategoryEntity getCategoryById(Long categoryId) {
        return null;
    }

    @Override
    public CategoryEntity createCategory(CategoryRequest request) {
        CategoryEntity entity = new CategoryEntity();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setId(null); // force INSERT

        return categoryRepository.save(entity);
    }

    @Override
    public CategoryEntity createCategory(CategoryEntity categoryEntity) {
        return null;
    }

    @Override
    public CategoryEntity updateCategory(CategoryEntity categoryEntity) {
        return null;
    }

    @Override
    public void deleteCategory(Long categoryId) {

    }
}
