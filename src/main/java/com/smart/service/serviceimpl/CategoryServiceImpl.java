package com.smart.service.serviceimpl;

import com.smart.service.dtoRequest.CategoryRequest;
import com.smart.service.entity.CategoryEntity;
import com.smart.service.entity.UserEntity;
import com.smart.service.enums.enums;
import com.smart.service.exception.ForbiddenException;
import com.smart.service.repository.CategoryRepository;
import com.smart.service.repository.UserRepository;
import com.smart.service.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;



    @Override
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity getCategoryById(Long categoryId) {

        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
    }


    @Override
    public CategoryEntity createCategory(CategoryRequest request, String email) {

        UserEntity users = userRepository.findByEmail(email);


        boolean isAdmin = users.getRoles().stream()
                .anyMatch(role -> role.getName() == enums.ADMIN);
        /*
         *.anyMatch() is a Stream method in Java
         * It checks if at least one element in a collection matches a condition
         * Returns a boolean (true or false)
         */
         if (!isAdmin){
             throw new ForbiddenException("You are not allowed to create this category");

         }

        CategoryEntity entity = new CategoryEntity();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setUsers(users);

        return categoryRepository.save(entity);
    }


    @Override
    public CategoryEntity updateCategory(Long categoryId, CategoryRequest request, String email) {

        // Find the user making the request
        UserEntity users = userRepository.findByEmail(email);

        // Check if the user is ADMIN
        boolean isAdmin = users.getRoles().stream()
                .anyMatch(role -> role.getName() == enums.ADMIN);

        if (!isAdmin) {
            throw new ForbiddenException("You are not allowed to update this category");
        }

        // Find the category to update
        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

        // Update fields
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        // Save changes
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId, String email) {
         
        // Check if the user is ADMIN
        UserEntity users = userRepository.findByEmail(email);

        boolean isAdmin = users.getRoles().stream()
                .anyMatch(role -> role.getName() == enums.ADMIN);

        if (!isAdmin) {
            throw new ForbiddenException("You are not allowed to delete this category");
        }

       categoryRepository.deleteById(categoryId);
    }
}
