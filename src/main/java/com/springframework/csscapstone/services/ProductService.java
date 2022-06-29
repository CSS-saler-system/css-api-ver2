package com.springframework.csscapstone.services;

import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.request_dto.admin.ProductCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.ProductUpdaterReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductCountOrderResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResDto;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductInvalidException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ProductService {

    PageImplResDto<ProductResDto> findAllProductByIdEnterprise(
            UUID idEnterprise,
            String name,
            String brand,
            Long inStock,
            Double minPrice,
            Double maxPrice,
            Double minPoint,
            Double maxPoint,
            Integer pageNumber,
            Integer pageSize);

    PageImplResDto<ProductResDto> findAllProductByCollaborator(
//            UUID enterpriseId,
            String name,
            String brand,
            Long inStock,
            Double minPrice,
            Double maxPrice,
            Double minPoint,
            Double maxPoint,
            Integer pageNumber,
            Integer pageSize);

    List<ProductResDto> findProductByIdAccount(UUID accountId) throws AccountNotFoundException;


    ProductResDto findById(UUID id) throws ProductNotFoundException;

    UUID createProduct(
            ProductCreatorReqDto dto,
            List<MultipartFile> typeImages,
            List<MultipartFile> certificationImages)
            throws ProductNotFoundException, ProductInvalidException, AccountNotFoundException, IOException;

    UUID updateProductDto(
            ProductUpdaterReqDto dto,
            List<MultipartFile> normalType,
            List<MultipartFile> certificationType) throws ProductNotFoundException, ProductInvalidException;

    void changeStatusProduct(UUID id, ProductStatus status);

    void disableProduct(UUID id);

    PageImplResDto<ProductCountOrderResDto> getListProductWithCountOrder(
            UUID id, LocalDate startDate, LocalDate endDate, Integer pageNumber, Integer pageSize)
            throws AccountNotFoundException;
}
