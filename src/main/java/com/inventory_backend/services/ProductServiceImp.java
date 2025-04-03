package com.inventory_backend.services;

import com.inventory_backend.entities.Category;
import com.inventory_backend.entities.Product;
import com.inventory_backend.repositories.CategoryRepo;
import com.inventory_backend.repositories.ProductRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    public ProductServiceImp(ProductRepo productRepo, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Product findById(long id) {
        Optional<Product> product = productRepo.findById(id);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new EntityNotFoundException("product with id " + id + " not found");
        }
    }

    @Override
    public Product addProduct(Product product) throws IOException {
         Optional<Category> category = categoryRepo.findById(product.getCategory().getId());
         if (category.isPresent()) {
             product.setCategory(category.get());
         } else {
             throw new EntityNotFoundException("category with id " + product.getCategory().getId() + " not found");
         }
        return productRepo.save(product);
    }
    @Override
    public Product updateProduct(Long id, Product productUpdates) throws IOException {
        Optional<Product> productOptional = productRepo.findById(id);
        if (productOptional.isPresent()) {
            Product existingProduct = productOptional.get();

            // Only update fields that are provided (not null)
            if (productUpdates.getName() != null) {
                existingProduct.setName(productUpdates.getName());
            }

            if (productUpdates.getDescription() != null) {
                existingProduct.setDescription(productUpdates.getDescription());
            }

            if (productUpdates.getPrice() != null) {
                existingProduct.setPrice(productUpdates.getPrice());
            }

            // Update category if provided
            if (productUpdates.getCategory() != null && productUpdates.getCategory().getId() != null) {
                Optional<Category> categoryOptional = categoryRepo.findById(productUpdates.getCategory().getId());
                if (categoryOptional.isPresent()) {
                    Category category = categoryOptional.get();
                    existingProduct.setCategory(category);
                } else {
                    throw new EntityNotFoundException("category with id " + productUpdates.getCategory().getId() + " not found");
                }
            }

            return productRepo.save(existingProduct);
        } else {
            throw new EntityNotFoundException("product with id " + id + " not found");
        }
    }


    @Override
    public void deleteProduct(Long id) throws IOException {
        Optional<Product> productOptional = productRepo.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            productRepo.delete(product);
        } else {
            throw new EntityNotFoundException("product with id " + id + " not found");
        }
    }
}