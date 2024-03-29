package com.springframework.csscapstone.services;

import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.request_dto.admin.ProductCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.ProductUpdaterReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.admin.ProductForModeratorResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.ProductForCollabGetDetailResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.ProductForCollaboratorResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductCountOrderResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductDetailEnterpriseDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResDto;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductInvalidException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface ProductService {

    PageImplResDto<ProductResDto> findAllProductByIdEnterprise(
            UUID idEnterprise, String name, String brand, ProductStatus productStatus,
            Double minPrice, Double maxPrice,
            Double minPoint, Double maxPoint,
            Integer pageNumber, Integer pageSize);

    PageImplResDto<ProductResDto> findAllProductForCollaborator(
//            UUID enterpriseId,
            String name, String brand, Long inStock,
            Double minPrice, Double maxPrice, Double minPoint,
            Double maxPoint, Integer pageNumber, Integer pageSize);

    PageImplResDto<ProductCountOrderResDto> getListProductWithCountOrder(
            UUID id, LocalDate startDate, LocalDate endDate, Integer pageNumber, Integer pageSize)
            throws AccountNotFoundException;

    PageImplResDto<ProductForCollaboratorResDto> pageProductWithNoRegisteredByEnterpriseAndCollaborator(
            UUID collaboratorId, UUID enterpriseId, Integer pageNumber, Integer pageSize);

    PageImplResDto<ProductForCollaboratorResDto> pageProductWithRegisteredByEnterpriseAndCollaborator(
            UUID collaboratorId, UUID enterpriseId, Integer pageNumber, Integer pageSize);

    PageImplResDto<ProductForModeratorResDto> pageAllForProductForModerator(
            String name, String nameEnterprise, String brand, ProductStatus status,
            Integer pageNumber, Integer pageSize);

    Optional<ProductForCollabGetDetailResDto> findByIdForCollaborator(UUID productId);

    List<ProductResDto> findProductByIdEnterprise(UUID accountId) throws AccountNotFoundException;


    ProductDetailEnterpriseDto findById(UUID id) throws ProductNotFoundException;

    UUID createProduct(
            ProductCreatorReqDto dto,
            List<MultipartFile> typeImages,
            List<MultipartFile> certificationImages)
            throws ProductNotFoundException, ProductInvalidException, AccountNotFoundException, IOException, ExecutionException, InterruptedException;

    UUID updateProductDto(
            UUID productId,
            ProductUpdaterReqDto dto,
            List<MultipartFile> normalType,
            List<MultipartFile> certificationType) throws ProductNotFoundException, ProductInvalidException, ExecutionException, InterruptedException;

    void changeStatusProduct(UUID id, ProductStatus status);

    void disableProduct(UUID id);
}
