package com.inventory_backend.services;

import com.inventory_backend.entities.Category;
import com.inventory_backend.repositories.CategoryRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {
    CategoryRepo categoryRepo;

    public CategoryServiceImp(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Category findById(Long id) {
        Optional<Category> category = categoryRepo.findById(id);
        if (category.isPresent()) {
            return category.get();
        } else {
            throw new EntityNotFoundException("category with id " + id + " not found");
        }

    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public Category updateCategory(Long id, Category categoryUpdates) {
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        if (categoryOptional.isPresent()) {
            Category existingCategory = categoryOptional.get();

            // Only update fields that are provided (not null)
            if (categoryUpdates.getName() != null) {
                existingCategory.setName(categoryUpdates.getName());
            }

            // Add other category fields here with null checks
            // For example:
            // if (categoryUpdates.getDescription() != null) {
            //     existingCategory.setDescription(categoryUpdates.getDescription());
            // }

            return categoryRepo.save(existingCategory);
        } else {
            throw new EntityNotFoundException("category with id " + id + " not found");
        }
    }
    @Override
    public void deleteCategory(Long id) {
        Optional<Category> categoryOptional = categoryRepo.findById(id);
        if (categoryOptional.isPresent()) {
            categoryRepo.delete(categoryOptional.get());
        } else {
            throw new EntityNotFoundException("category with id " + id + " not found");
        }
    }
}
