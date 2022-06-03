package com.springframework.csscapstone.services;

import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.basic.ProductDto;
import com.springframework.csscapstone.payload.queries.ProductQueriesResponseDto;
import com.springframework.csscapstone.payload.request_dto.admin.ProductCreatorDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.ProductUpdaterDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResponse;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResponseDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductWithQuantityDTO;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductInvalidException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ProductService {

    PageImplResponse<ProductResponseDto> findAllProduct(
            String name,
            String brand,
            Long inStock,
            Double minPrice,
            Double maxPrice,
            Double minPoint,
            Double maxPoint,
            ProductStatus productStatus, Integer pageNumber, Integer pageSize);

    List<ProductResponseDto> findProductByIdAccount(UUID accountId) throws AccountNotFoundException;


    ProductResponseDto findById(UUID id) throws ProductNotFoundException;

    UUID createProduct(
            ProductCreatorDto dto,
            List<MultipartFile> typeImages,
            List<MultipartFile> certificationImages) throws ProductNotFoundException, ProductInvalidException, AccountNotFoundException, IOException;

    UUID updateProductDto(
            ProductUpdaterDto dto,
            List<MultipartFile> normalType,
            List<MultipartFile> certificationType) throws ProductNotFoundException, ProductInvalidException;

    void changeStatusProduct(UUID id, ProductStatus status);

    void disableProduct(UUID id);

    PageImplResponse<ProductQueriesResponseDto> getListProductWithCountOrder(
            UUID id,
            LocalDate startDate,
            LocalDate endDate, Integer pageNumber, Integer pageSize);
}
