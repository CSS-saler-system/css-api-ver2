package com.springframework.csscapstone.css_data.repositories;

import com.springframework.csscapstone.css_data.domain.Product;
import com.springframework.csscapstone.css_data.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findProductImageByProduct(Product product);
}