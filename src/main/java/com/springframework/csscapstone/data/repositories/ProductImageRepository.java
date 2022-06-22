package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Product;
import com.springframework.csscapstone.data.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findProductImageByProduct(Product product);

    @Query("SELECT i FROM ProductImage i WHERE i.product.id = :productId")
    List<ProductImage> findProductImageByProduct(@Param("productId") UUID productId);

}