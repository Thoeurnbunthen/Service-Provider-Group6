package com.smart.service.controller;


import com.smart.service.dtoRequest.CategoryRequest;
import com.smart.service.entity.CategoryEntity;
import com.smart.service.service.CategoryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/categories")
    @PreAuthorize("hasRole('ADMIN')") // simple role check before entering the method
    public ResponseEntity<CategoryEntity> create (@RequestBody CategoryRequest categoryRequest, Principal principal){
        CategoryEntity createdCategory = categoryService.createCategory(categoryRequest, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/categories/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryEntity> update(
            @PathVariable("id") Long categoryId,
            @RequestBody CategoryRequest categoryRequest,
            Principal principal) {

        CategoryEntity updatedCategory = categoryService.updateCategory(categoryId, categoryRequest, principal.getName());
        return ResponseEntity.ok(updatedCategory);
    }

      @GetMapping("/categories")
      public ResponseEntity<List<CategoryEntity>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
          
      }
      
      @DeleteMapping("/categories/{id}")
      public ResponseEntity<Map<String, Object>> deleteCategory(
              @PathVariable("id") Long categoryId,
              Principal principal) {

          categoryService.deleteCategory(categoryId, principal.getName());

          Map<String, Object> response = new HashMap<>();
          response.put("statue",  "success");
          response.put("message", "Category deleted successfully");
          response.put("timestamp",  System.currentTimeMillis());
          return ResponseEntity.ok(response);
      }

}
