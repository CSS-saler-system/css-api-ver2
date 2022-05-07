package com.springframework.csscapstone.services.business;

import com.springframework.csscapstone.data.domain.Product;
import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.services.model_dto.basic.ProductDto;
import com.springframework.csscapstone.services.model_dto.custom.creator_model.ProductCreatorDto;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductInvalidException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductDto> findAllProduct(
            String name, String brand,
            Double weight, String shortDescription,
            String description, Long inStock, Double price,
            Double pointSale, ProductStatus productStatus);

    List<ProductDto> findByIdAccount(UUID accountId) throws AccountNotFoundException;

    ProductDto findById(UUID id) throws ProductNotFoundException;

    UUID createProduct(ProductCreatorDto dto) throws ProductNotFoundException, ProductInvalidException;

    UUID updateProductDto(ProductDto dto) throws ProductNotFoundException, ProductInvalidException;

    void disableProduct(UUID id);

    List<ProductDto> getListProductByName(String name);



}
