package com.sangha.forum.controller;

import com.sangha.forum.dto.ApiResponse;
import com.sangha.forum.entity.Category;
import com.sangha.forum.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Category management APIs")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @Operation(summary = "Create a new category")
    public ResponseEntity<ApiResponse<Category>> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(ApiResponse.success("Category created successfully", 
            categoryService.createCategory(category)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a category")
    public ResponseEntity<ApiResponse<Category>> updateCategory(
            @PathVariable Long id,
            @RequestBody Category category) {
        return ResponseEntity.ok(ApiResponse.success("Category updated successfully", 
            categoryService.updateCategory(id, category)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ApiResponse.success("Category deleted successfully", null));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID")
    public ResponseEntity<ApiResponse<Category>> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Category retrieved successfully", 
            categoryService.getCategoryById(id)));
    }

    @GetMapping
    @Operation(summary = "Get all categories")
    public ResponseEntity<ApiResponse<List<Category>>> getAllCategories() {
        return ResponseEntity.ok(ApiResponse.success("Categories retrieved successfully", 
            categoryService.getAllCategories()));
    }

    @GetMapping("/top-level")
    @Operation(summary = "Get top-level categories")
    public ResponseEntity<ApiResponse<List<Category>>> getTopLevelCategories() {
        return ResponseEntity.ok(ApiResponse.success("Top-level categories retrieved successfully", 
            categoryService.getTopLevelCategories()));
    }

    @GetMapping("/{parentId}/subcategories")
    @Operation(summary = "Get subcategories of a category")
    public ResponseEntity<ApiResponse<List<Category>>> getSubcategories(@PathVariable Long parentId) {
        return ResponseEntity.ok(ApiResponse.success("Subcategories retrieved successfully", 
            categoryService.getSubcategories(parentId)));
    }

    @GetMapping("/search")
    @Operation(summary = "Search categories by name")
    public ResponseEntity<ApiResponse<List<Category>>> searchCategories(@RequestParam String name) {
        return ResponseEntity.ok(ApiResponse.success("Categories retrieved successfully", 
            categoryService.searchCategories(name)));
    }
} 