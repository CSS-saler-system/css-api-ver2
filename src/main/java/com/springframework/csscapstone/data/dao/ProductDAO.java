package com.springframework.csscapstone.data.dao;

import com.springframework.csscapstone.controller.domain.Product;
import com.springframework.csscapstone.data.status.ProductStatus;

import java.util.List;

public interface ProductDAO {
    List<Product> searchProduct(String name, String brand, Double weight,
                                String shortDescription, String description,
                                Long inStock, Double price, Double pointSale,
                                ProductStatus productStatus);
}
