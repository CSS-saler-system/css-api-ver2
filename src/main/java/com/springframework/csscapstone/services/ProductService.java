package com.springframework.csscapstone.services;

import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.basic.ProductDto;
import com.springframework.csscapstone.payload.request_dto.admin.ProductCreatorDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResponse;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResponseDto;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductInvalidException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ProductService {

    PageImplResponse<ProductDto> findAllProduct(
            String name,
            String brand,
            Long inStock,
            Double minPrice,
            Double maxPrice,
            Double minPoint,
            Double maxPoint,
            ProductStatus productStatus, Integer pageNumber, Integer pageSize);

    List<ProductDto> findProductByIdAccount(UUID accountId) throws AccountNotFoundException;

    ProductResponseDto findById(UUID id) throws ProductNotFoundException;

    UUID createProduct(
            ProductCreatorDto dto,
            List<MultipartFile> typeImages,
            List<MultipartFile> certificationImages) throws ProductNotFoundException, ProductInvalidException, AccountNotFoundException, IOException;

    UUID updateProductDto(ProductDto dto) throws ProductNotFoundException, ProductInvalidException;

    void changeStatusProduct(UUID id, ProductStatus status);

    void disableProduct(UUID id);

}
