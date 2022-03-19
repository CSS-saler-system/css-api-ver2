package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Category;
import com.springframework.csscapstone.data.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findProductByCategory(Category category);
}