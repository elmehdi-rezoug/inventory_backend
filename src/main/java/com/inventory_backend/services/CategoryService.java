package com.inventory_backend.services;

import com.inventory_backend.entities.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    Category addCategory(Category category);
    Category updateCategory(Long id, Category category);
    void deleteCategory(Long id);
}
