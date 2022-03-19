package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.controller.domain.Product;
import com.springframework.csscapstone.controller.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findProductImageByProduct(Product product);
}