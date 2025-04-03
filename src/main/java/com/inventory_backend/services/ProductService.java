package com.inventory_backend.services;

import com.inventory_backend.entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
     List<Product> findAll() throws IOException;
     Product findById(long id) throws IOException;
     Product addProduct(Product product) throws IOException;
     Product updateProduct(Long id, Product product) throws IOException;
     void deleteProduct(Long id) throws IOException;
}
