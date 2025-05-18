package com.sangha.forum.service.impl;

import com.sangha.forum.entity.Category;
import com.sangha.common.exception.ResourceNotFoundException;
import com.sangha.forum.repository.CategoryRepository;
import com.sangha.forum.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category createCategory(Category category) {
        if (category.getParent() != null && category.getParent().getId() != null) {
            Category parent = getCategoryById(category.getParent().getId());
            category.setParent(parent);
        }
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, Category category) throws ResourceNotFoundException {
        Category existingCategory = getCategoryById(id);
        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        
        if (category.getParent() != null && category.getParent().getId() != null) {
            Category parent = getCategoryById(category.getParent().getId());
            existingCategory.setParent(parent);
        } else {
            existingCategory.setParent(null);
        }
        
        return categoryRepository.save(existingCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) throws ResourceNotFoundException {
        Category category = getCategoryById(id);
        if (!category.getSubcategories().isEmpty()) {
            throw new IllegalStateException("Cannot delete category with subcategories");
        }
        categoryRepository.delete(category);
    }

    @Override
    public Category getCategoryById(Long id) throws ResourceNotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> getTopLevelCategories() {
        return categoryRepository.findByParentIsNull();
    }

    @Override
    public List<Category> getSubcategories(Long parentId) {
        return categoryRepository.findByParentId(parentId);
    }

    @Override
    public List<Category> searchCategories(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }
} 