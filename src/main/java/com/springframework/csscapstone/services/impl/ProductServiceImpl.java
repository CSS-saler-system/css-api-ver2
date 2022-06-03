package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.dao.specifications.ProductSpecifications;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Category;
import com.springframework.csscapstone.data.domain.Product;
import com.springframework.csscapstone.data.domain.ProductImage;
import com.springframework.csscapstone.data.repositories.*;
import com.springframework.csscapstone.data.status.OrderStatus;
import com.springframework.csscapstone.data.status.ProductImageType;
import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.queries.ProductQueriesResponseDto;
import com.springframework.csscapstone.payload.queries.QueriesProductDto;
import com.springframework.csscapstone.payload.request_dto.admin.ProductCreatorDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.ProductUpdaterDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResponse;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResponseDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductWithQuantityDTO;
import com.springframework.csscapstone.services.ProductService;
import com.springframework.csscapstone.utils.blob_utils.BlobUploadImages;
import com.springframework.csscapstone.utils.exception_utils.category_exception.CategoryNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductInvalidException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.MapperDTO;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.MapperProductQueriesDto;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:application-storage.properties")
public class ProductServiceImpl implements ProductService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Value("${product_image_container}")
    private String productContainer;

    @Value("${endpoint}")
    private String endpoint;

    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository imageRepository;
    private final BlobUploadImages blobUploadImages;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    public PageImplResponse<ProductResponseDto> findAllProduct(
            String name, String brand, Long inStock, Double minPrice, Double maxPrice,
            Double minPoint, Double maxPoint, ProductStatus productStatus,
            Integer pageNumber, Integer pageSize) {

        Specification<Product> search = Specification
                .where(StringUtils.isBlank(name) ? null : ProductSpecifications.nameContains(name))
                .and(StringUtils.isBlank(brand) ? null : ProductSpecifications.brandContains(brand))
                .and(Objects.isNull(minPrice) ? null : ProductSpecifications.priceGreaterThan(minPrice))
                .and(Objects.isNull(maxPrice) ? null : ProductSpecifications.priceLessThan(maxPrice))
                .and(Objects.isNull(minPoint) ? null : ProductSpecifications.pointGreaterThan(minPoint))
                .and(Objects.isNull(maxPoint) ? null : ProductSpecifications.pointLessThan(maxPoint))
                .and(Objects.isNull(productStatus) ? null : ProductSpecifications.statusEquals(productStatus));

        pageSize = Objects.isNull(pageSize) || (pageSize <= 1) ? 50 : pageSize;
        pageNumber = Objects.isNull(pageNumber) || (pageNumber <= 1) ? 1 : pageNumber;

        Page<Product> page = this.productRepository.findAll(search, PageRequest.of(pageNumber - 1, pageSize));

        List<ProductResponseDto> data = page.stream().map(MapperDTO.INSTANCE::toProductResponseDto).collect(toList());

        return new PageImplResponse<>(
                data, page.getNumber() + 1, page.getSize(),
                page.getTotalElements(), page.getTotalPages(),
                page.isFirst(), page.isLast());
    }

    /**
     * todo find product by account <Completed></>
     */
    @Override
    public List<ProductResponseDto> findProductByIdAccount(UUID accountId) throws AccountNotFoundException {
        Account account = this.accountRepository.findById(accountId).orElseThrow(handlerAccountNotFound());
        return account.getProducts().stream().map(MapperDTO.INSTANCE::toProductResponseDto).collect(toList());
    }

    @Override
    public ProductResponseDto findById(UUID id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .map(MapperDTO.INSTANCE::toProductResponseDto)
                .orElseThrow(handlerProductNotFound());
    }

    /**
     * dto has validation exception on account_id and category_id so no need checking null them
     *
     * @param dto
     * @return
     * @throws ProductInvalidException
     * @throws AccountNotFoundException
     */
    @Transactional
    @Override
    public UUID createProduct(
            ProductCreatorDto dto,
            List<MultipartFile> typeImages,
            List<MultipartFile> certificationImages)
            throws ProductInvalidException, AccountNotFoundException, IOException {
        //check null category
        if (Objects.isNull(dto.getCategoryId())) throw handlerCategoryNotFound().get();
        //check null account
        if (Objects.isNull(dto.getCreatorAccountId())) throw handlerAccountCreatorNotFound().get();

        //check existed account
        Account account = accountRepository
                .findById(dto.getCreatorAccountId())
                .orElseThrow(handlerAccountCreatorNotFound());

        //check existed category
        Category category = categoryRepository
                .findById(dto.getCategoryId())
                .orElseThrow(handlerCategoryNotFound());
        //create product
        Product newProduct = new Product()
                .setName(dto.getName())
                .setBrand(dto.getBrand())
                .setPrice(dto.getPrice())
                .setDescription(dto.getDescription())
                .setShortDescription(dto.getShortDescription())
                .setProductStatus(ProductStatus.ACTIVE)
                .setQuantityInStock(dto.getQuantity())
                .setPointSale(dto.getPointSale())
                .addAccount(account)
                .addCategory(category);
        //saved product
        Product creatorProduct = productRepository.save(newProduct);
        //add image to product
        Product savedProduct = handleImage(typeImages, certificationImages, creatorProduct);

        return this.productRepository.save(savedProduct).getId();
    }

    //TODO BUG
    private Product handleImage(List<MultipartFile> typeImages, List<MultipartFile> certificate, Product entity) {
        if (!typeImages.isEmpty()) {
            saveProductImageEntity(typeImages, entity.getId(), ProductImageType.NORMAL)
                    .ifPresent(entity::addProductImage);
        }
        if (!certificate.isEmpty()) {
            saveProductImageEntity(certificate, entity.getId(), ProductImageType.CERTIFICATION)
                    .ifPresent(entity::addProductImage);
        }
        return entity;
    }

    private Optional<ProductImage[]> saveProductImageEntity(
            List<MultipartFile> images, UUID id, ProductImageType type) {
        if (!images.isEmpty()) {
            String nameImageOnAzure = id + "/";

            Map<String, MultipartFile> imageMap = images.stream()
                    .collect(Collectors.toMap(x -> nameImageOnAzure + x.getOriginalFilename(), x -> x));

            imageMap.forEach(blobUploadImages::azureProductStorageHandler);

            ProductImage[] productImages = imageMap.keySet()
                    .stream()
                    .map(x -> endpoint + productContainer + "/" + x)
                    .peek(x -> System.out.println("This is image: " + x))
                    .map(name -> new ProductImage().setPath(name).setType(type))
                    .peek(this.imageRepository::save)
                    .toArray(ProductImage[]::new);
            return Optional.of(productImages);
        }
        return Optional.empty();
    }

    //TODO Changing
    @Transactional
    @Override
    public UUID updateProductDto(ProductUpdaterDto dto,
                                 List<MultipartFile> normalType,
                                 List<MultipartFile> certificationType) throws ProductNotFoundException, ProductInvalidException {
        if (dto.getId() == null) throw handlerProductInvalidException().get();

        Product entity = this.productRepository
                .findById(dto.getId())
                .orElseThrow(handlerProductNotFound());

        entity.setName(dto.getName())
                .setBrand(dto.getBrand())
                .setDescription(dto.getDescription())
                .setShortDescription(dto.getShortDescription())
                .setPointSale(dto.getPointSale())
                .setPrice(dto.getPrice())
                .setQuantityInStock(dto.getQuantity());

        Product product = imageHandler(normalType, certificationType, entity);

        this.productRepository.save(product);
        return entity.getId();
    }

    private Product imageHandler(List<MultipartFile> normalType, List<MultipartFile> certificationType, Product entity) {
        if (Objects.nonNull(normalType) && !normalType.isEmpty()) {
            saveProductImage(normalType, entity.getId(), ProductImageType.NORMAL)
                    .ifPresent(entity::addProductImage);
        }
        if (Objects.nonNull(normalType) && !normalType.isEmpty()) {
            saveProductImage(certificationType, entity.getId(), ProductImageType.CERTIFICATION)
                    .ifPresent(entity::addProductImage);
        }
        return entity;
    }

    //create ProductImage
    //save in database
    private Optional<ProductImage[]> saveProductImage(List<MultipartFile> normalType, UUID id, ProductImageType type) {
        String nameImageOnAzure = id + "/";
        Map<String, MultipartFile> collect = normalType.stream()
                .peek(x -> System.out.println("Image: " + nameImageOnAzure + x.getOriginalFilename()))
                .collect(Collectors.toMap(
                        x -> nameImageOnAzure + x.getOriginalFilename(),
                        x -> x));
        //update to azure
        collect.forEach(blobUploadImages::azureProductStorageHandler);

        return Optional.of(collect.keySet()
                        .stream()
//                 .map(x -> endpoint + productContainer + "/" + x)
                        .map(name -> new ProductImage(type,
                                endpoint + this.productContainer + "/" + name))
                        .peek(this.imageRepository::save)
                        .toArray(ProductImage[]::new)
        );

    }


    @Override
    public void changeStatusProduct(UUID id, ProductStatus status) {
        this.productRepository.findById(id)
//                .filter(product -> product.getProductStatus().equals(ProductStatus.DISABLE))
                .ifPresent(product -> {
                    product.setProductStatus(status);
                    this.productRepository.save(product);
                });
    }

    @Transactional
    @Override
    public void disableProduct(UUID id) {
        this.productRepository.findById(id)
                .ifPresent(x -> {
                    x.setProductStatus(ProductStatus.DISABLE);
                    this.productRepository.save(x);
                });
    }

    /**
     * todo Get all response dto and counter
     * @param id
     * @param startDate
     * @param endDate
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public PageImplResponse<ProductQueriesResponseDto> getListProductWithCountOrder(
            UUID id, LocalDate startDate, LocalDate endDate, Integer pageNumber, Integer pageSize) {

        pageNumber = Objects.isNull(pageNumber) || pageNumber <= 1 ? 1 : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize <= 1 ? 1 : pageSize;
        Page<QueriesProductDto> pageResults = this.orderDetailRepository
                .findAllSumInOrderDetailGroupingByProduct(
                        id, startDate.atStartOfDay(),
                        endDate.atTime(23, 59, 29),
                        OrderStatus.FINISH,
                        PageRequest.of(pageNumber - 1, pageSize));

        List<ProductQueriesResponseDto> list = pageResults.getContent()
                .stream()
                        .map(product -> MapperProductQueriesDto.INSTANCE
                        .toProductQueriesDto(product.getProduct(), product.getSumQuantity()))
                .collect(toList());

        return new PageImplResponse<>(
                list, pageResults.getNumber(),
                pageResults.getContent().size(),
                pageResults.getTotalElements(), pageResults.getTotalPages(),
                pageResults.isFirst(), pageResults.isLast());
    }

    //===================Utils Methods====================
    //====================================================
    private Supplier<ProductNotFoundException> handlerProductNotFound() {
        return () -> new ProductNotFoundException(MessagesUtils.getMessage(MessageConstant.Product.NOT_FOUND));
    }

    private Supplier<CategoryNotFoundException> handlerCategoryNotFound() {
        return () -> new CategoryNotFoundException(MessagesUtils.getMessage(MessageConstant.Category.NOT_FOUND));
    }

    private Supplier<AccountNotFoundException> handlerAccountCreatorNotFound() {
        return () -> new AccountNotFoundException(MessagesUtils.getMessage(MessageConstant.Account.CREATOR_NOT_FOUND));
    }

    private Supplier<AccountNotFoundException> handlerAccountNotFound() {
        return () -> new AccountNotFoundException(MessagesUtils.getMessage(MessageConstant.Account.NOT_FOUND));
    }

    private Supplier<ProductInvalidException> handlerProductInvalidException() {
        return () -> new ProductInvalidException(MessagesUtils.getMessage(MessageConstant.Product.INVALID));
    }
}
