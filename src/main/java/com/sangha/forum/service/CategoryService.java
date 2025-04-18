package com.sangha.forum.service;

import com.sangha.forum.entity.Category;
import com.sangha.forum.exception.ResourceNotFoundException;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category updateCategory(Long id, Category category) throws ResourceNotFoundException;
    void deleteCategory(Long id) throws ResourceNotFoundException;
    Category getCategoryById(Long id) throws ResourceNotFoundException;
    List<Category> getAllCategories();
    List<Category> getTopLevelCategories();
    List<Category> getSubcategories(Long parentId);
    List<Category> searchCategories(String name);
} 