package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.config.message.constant.MessageConstant;
import com.springframework.csscapstone.data.dao.specifications.ProductSpecifications;
import com.springframework.csscapstone.data.domain.*;
import com.springframework.csscapstone.data.repositories.*;
import com.springframework.csscapstone.data.status.OrderStatus;
import com.springframework.csscapstone.data.status.ProductImageType;
import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.queries.NumberProductOrderedQueryDto;
import com.springframework.csscapstone.payload.request_dto.admin.ProductCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.ProductUpdaterReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.admin.ProductForModeratorResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.ProductForCollabGetDetailResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.ProductForCollaboratorResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductCountOrderResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductDetailEnterpriseDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResDto;
import com.springframework.csscapstone.services.ProductService;
import com.springframework.csscapstone.utils.blob_utils.BlobUploadImages;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.category_exception.CategoryNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductInvalidException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.MapperDTO;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.MapperQueriesDTO;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.ProductMapper;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Tuple;
import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:application-storage.properties")
public class ProductServiceImpl implements ProductService {
    private static final String ALL_PRODUCT_BY_ENTERPRISE = "findAllProductByIdEnterprise";
    private static final String PRODUCT_FOR_COLLABORATOR = "findAllProductForCollaborator";
    private static final String PRODUCT_BY_ID_FOR_ENTERPRISE = "productByIdForEnterprise";
    private static final String PRODUCT_BY_ID = "productById";
    private static final String LIST_PRODUCT_COUNTER_ORDER = "getListProductWithCountOrder";
    private static final String LIST_PRODUCT_NO_REGISTERED_FOR_COLLABORATOR = "productWithoutRegisteredStatus";
    private static final String LIST_PRODUCT_REGISTERED_FOR_COLLABORATOR = "productWithRegisteredStatus";
    private static final String LIST_PRODUCT_FOR_COLLABORATOR = "listProductForCollaborator";
    private static final String PRODUCT_ID_FOR_COLLABORATOR = "productByIdForCollaborator";
    private final CacheManager cacheManager;
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

    /**
     * todo excludes disable product
     *
     * @param idEnterprise
     * @param name
     * @param brand
     * @param minPrice
     * @param maxPrice
     * @param minPoint
     * @param maxPoint
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    @Cacheable(key = "{#p0, #p1, #p2, #p3, #p4, #p5, #p6, #p7, #p8}", value = ALL_PRODUCT_BY_ENTERPRISE)
    public PageImplResDto<ProductResDto> findAllProductByIdEnterprise(
            UUID idEnterprise, String name,
            String brand, ProductStatus productStatus, Double minPrice, Double maxPrice,
            Double minPoint, Double maxPoint,
            Integer pageNumber, Integer pageSize) {

        Account account = this.accountRepository.findById(idEnterprise)
                .orElseThrow(() -> new EntityNotFoundException("Enterprise with id: " + idEnterprise + "was not found"));

        Specification<Product> search = Specification
                .where(ProductSpecifications.enterpriseId(account))
                .and(StringUtils.isBlank(name) ? null : ProductSpecifications.nameContains(name))
                .and(StringUtils.isBlank(brand) ? null : ProductSpecifications.brandContains(brand))
                .and(Objects.isNull(minPrice) ? null : ProductSpecifications.priceGreaterThan(minPrice))
                .and(Objects.isNull(productStatus) ? null : ProductSpecifications.filterByStatus(productStatus))
                .and(Objects.isNull(maxPrice) ? null : ProductSpecifications.priceLessThan(maxPrice))
                .and(Objects.isNull(minPoint) ? null : ProductSpecifications.pointGreaterThan(minPoint))
                .and(Objects.isNull(maxPoint) ? null : ProductSpecifications.pointLessThan(maxPoint))
                .and(ProductSpecifications.excludeDeleteStatus());

        return getProductResDtoPageImplResDto(pageNumber, pageSize, search);
    }

    //TODO For Collaborator get all product
    @Override
    @Cacheable(key = "{#p0, #p1, #p2, #p3, #p4, #p5, #p6, #p7}", value = PRODUCT_FOR_COLLABORATOR)
    public PageImplResDto<ProductResDto> findAllProductForCollaborator(
            String name, String brand, Long inStock,
            Double minPrice, Double maxPrice,
            Double minPoint, Double maxPoint,
            Integer pageNumber, Integer pageSize) {

        Specification<Product> search = Specification
                .where(StringUtils.isBlank(name) ? null : ProductSpecifications.nameContains(name))
                .and(StringUtils.isBlank(brand) ? null : ProductSpecifications.brandContains(brand))
                .and(Objects.isNull(minPrice) ? null : ProductSpecifications.priceGreaterThan(minPrice))
                .and(Objects.isNull(maxPrice) ? null : ProductSpecifications.priceLessThan(maxPrice))
                .and(Objects.isNull(minPoint) ? null : ProductSpecifications.pointGreaterThan(minPoint))
                .and(Objects.isNull(maxPoint) ? null : ProductSpecifications.pointLessThan(maxPoint))
                .and(ProductSpecifications.excludeDeleteStatus());
        return getProductResDtoPageImplResDto(pageNumber, pageSize, search);
    }

    /**
     * todo find product by account <Completed></>
     */
    @Override
    @Cacheable(key = "#p0", value = PRODUCT_BY_ID_FOR_ENTERPRISE)
    public List<ProductResDto> findProductByIdEnterprise(UUID accountId) throws AccountNotFoundException {
        Account account = this.accountRepository.findById(accountId).orElseThrow(handlerAccountNotFound);
        return account.getProducts().stream()
                .filter(product -> !product.getProductStatus().equals(ProductStatus.DISABLED))
                .map(MapperDTO.INSTANCE::toProductResDto).collect(toList());
    }

    @Override
    @Cacheable(key = "#p0", value = PRODUCT_BY_ID)
    public ProductDetailEnterpriseDto findById(UUID id) throws ProductNotFoundException {
        return productRepository.findById(id)
                .filter(product -> !product.getProductStatus().equals(ProductStatus.DISABLED))
                .map(MapperDTO.INSTANCE::toProductDetailEnterpriseDto)
                .orElseThrow(handlerProductNotFound);
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
            ProductCreatorReqDto dto, List<MultipartFile> typeImages,
            List<MultipartFile> certificationImages)
            throws ProductInvalidException, AccountNotFoundException {

        //check null category
        if (Objects.isNull(dto.getCategoryId())) throw handlerCategoryNotFound.get();

        //check null account
        if (Objects.isNull(dto.getCreatorAccountId())) throw handlerAccountCreatorNotFound.get();

        //check existed account
        Account account = accountRepository
                .findById(dto.getCreatorAccountId())
                .orElseThrow(handlerAccountCreatorNotFound);

        //check existed category
        Category category = categoryRepository
                .findById(dto.getCategoryId())
                .orElseThrow(handlerCategoryNotFound);

        //create product
        Product newProduct = new Product()
                .setName(dto.getName())
                .setBrand(dto.getBrand())
                .setPrice(dto.getPrice())
                .setDescription(dto.getDescription())
                .setShortDescription(dto.getShortDescription())
                .setProductStatus(ProductStatus.ACTIVE)
                .setPointSale(dto.getPointSale())
                .addAccount(account)
                .addCategory(category);

        //saved product
        Product creatorProduct = productRepository.save(newProduct);

        //add image to product
        Product savedProduct = handleImage(typeImages, certificationImages, creatorProduct);
        clearCache();
        return this.productRepository.save(savedProduct).getId();
    }

    //TODO Changing
    @Transactional
    @Override
    public UUID updateProductDto(UUID productId,
                                 ProductUpdaterReqDto dto,
                                 List<MultipartFile> normalType,
                                 List<MultipartFile> certificationType)
            throws ProductNotFoundException, ProductInvalidException {

        Product entity = this.productRepository
                .findById(productId)
                .filter(product -> !product.getProductStatus().equals(ProductStatus.DISABLED))
                .orElseThrow(handlerProductNotFound);

        entity.setName(dto.getName())
                .setBrand(dto.getBrand())
                .setDescription(dto.getDescription())
                .setShortDescription(dto.getShortDescription())
                .setPointSale(dto.getPointSale())
                .setPrice(dto.getPrice());

        Product product = imageHandler(normalType, certificationType, entity);

        this.productRepository.save(product);
        clearCache();
        return entity.getId();
    }

    //todo Notification
    @Transactional
    @Override
    public void changeStatusProduct(UUID id, ProductStatus status) {
        this.productRepository.findById(id)
//                .filter(product -> product.getProductStatus().equals(ProductStatus.DISABLE))
                .ifPresent(product -> {
                    product.setProductStatus(status);
                    this.productRepository.save(product);
                    clearCache();
                });
    }

    @Transactional
    @Override
    public void disableProduct(UUID id) {
        this.productRepository.findById(id)
                .ifPresent(x -> {
//                    x.setProductStatus(ProductStatus.DISABLE);
                    x.setProductStatus(ProductStatus.DELETED);
                    this.productRepository.save(x);
                    clearCache();
                });
    }

    @Override
    @Cacheable(key = "{#p0, #p1, #p2, #p3, #p4}", value = LIST_PRODUCT_COUNTER_ORDER)
    public PageImplResDto<ProductCountOrderResDto> getListProductWithCountOrder(
            UUID id, LocalDate startDate, LocalDate endDate, Integer pageNumber, Integer pageSize) throws AccountNotFoundException {
        //throws exception if id not found
        if (Objects.isNull(id)) throw handlerAccountNotFound.get();

        pageNumber = Objects.isNull(pageNumber) || pageNumber <= 1 ? 1 : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize <= 1 ? 1 : pageSize;

        //get sum number in order-detail of order in during start date and end date
        Page<Tuple> page = this.orderDetailRepository.findAllSumInOrderDetailGroupingByProduct(
                id, startDate.atStartOfDay(), endDate.atStartOfDay(),
                OrderStatus.FINISHED, PageRequest.of(pageNumber - 1, pageSize, Sort.by(Product_.NAME)));

        //create numberProductOrderedQueryDto
        Map<Product, Long> collect = page.getContent()
                .stream()
                .collect(toMap(
                        tuple -> this.productRepository
                                .findById(tuple.get(OrderDetailRepository.PRODUCT, UUID.class))
                                .orElse(new Product()),
                        tuple -> tuple.get(OrderDetailRepository.COUNT, Long.class)));
        //con-create the ProductCountOrderResDto
        List<ProductCountOrderResDto> content = collect.entrySet().stream()
                .map(entry -> new NumberProductOrderedQueryDto(entry.getKey(), entry.getValue()))
                .map(MapperQueriesDTO.INSTANCE::toQueriesProductDto)
                .collect(toList());

        return new PageImplResDto<>(content, page.getNumber(), content.size(), page.getTotalElements(),
                page.getTotalPages(), page.isLast(), page.isFirst());
    }

    @Override
    @Cacheable(key = "{#p0, #p1, #p2, #p3}", value = LIST_PRODUCT_NO_REGISTERED_FOR_COLLABORATOR)
    public PageImplResDto<ProductForCollaboratorResDto> pageProductWithNoRegisteredByEnterpriseAndCollaborator(
            UUID collaboratorId, UUID enterpriseId, Integer pageNumber, Integer pageSize) {
        pageNumber = Objects.isNull(pageNumber) || pageNumber < 1 ? 1 : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize < 1 ? 10 : pageSize;

        Page<Product> page = this.productRepository.getAllProductNotRegister(
                collaboratorId, enterpriseId, PageRequest.of(pageNumber - 1, pageSize, Sort.by(Product_.NAME)));

        List<ProductForCollaboratorResDto> result = page.getContent()
                .stream()
                .sorted(Comparator.comparing(Product::getPointSale).reversed())
                .map(ProductMapper.INSTANCE::productToProductForCollaboratorResDto)
                .collect(toList());

        return new PageImplResDto<>(result, page.getNumber() + 1, result.size(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Override
    @Cacheable(key = "{#p0, #p1, #p2, #p3}", value = LIST_PRODUCT_REGISTERED_FOR_COLLABORATOR)
    public PageImplResDto<ProductForCollaboratorResDto> pageProductWithRegisteredByEnterpriseAndCollaborator(
            UUID collaboratorId, UUID enterpriseId, Integer pageNumber, Integer pageSize) {
        pageNumber = Objects.isNull(pageNumber) || pageNumber < 1 ? 1 : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize < 1 ? 10 : pageSize;
        Page<Product> page = this.productRepository.getAllProductRegister(
                collaboratorId, enterpriseId, PageRequest.of(pageNumber - 1, pageSize, Sort.by(Product_.NAME)));
        List<ProductForCollaboratorResDto> result = page.getContent()
                .stream()
                .sorted(Comparator.comparing(Product::getPointSale).reversed())
                .map(ProductMapper.INSTANCE::productToProductForCollaboratorResDto)
                .collect(toList());
        return new PageImplResDto<>(result, page.getNumber() + 1, result.size(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Override
    @Cacheable(key = "{#p0, #p1, #p2, #p3, #p4}", value = LIST_PRODUCT_FOR_COLLABORATOR)
    public PageImplResDto<ProductForModeratorResDto> pageAllForProductForModerator(
            String name, String nameEnterprise, String brand,
            Integer pageNumber, Integer pageSize) {

        pageNumber = Objects.isNull(pageNumber) || pageNumber < 1 ? 1 : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize < 1 ? 10 : pageSize;

        Specification<Product> search = Specification
                .where(StringUtils.isBlank(name) ? null : ProductSpecifications.nameContains(name))
                .and(StringUtils.isBlank(brand) ? null : ProductSpecifications.brandContains(brand))
                .and(StringUtils.isBlank(nameEnterprise) ? null : ProductSpecifications.enterpriseName(nameEnterprise));
        Page<Product> page = this.productRepository.findAll(search,
                PageRequest.of(pageNumber - 1, pageSize, Sort.by(Product_.CREATE_DATE)));
        List<ProductForModeratorResDto> result = page.getContent()
                .stream()
                .map(ProductMapper.INSTANCE::productToProductForModeratorResDto)
                .collect(toList());
        return new PageImplResDto<>(result, page.getNumber() + 1, result.size(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isFirst());
    }

    @Override
    @Cacheable(key = "#p0", value = PRODUCT_ID_FOR_COLLABORATOR)
    public Optional<ProductForCollabGetDetailResDto> findByIdForCollaborator(UUID productId) {
        return this.productRepository.findById(productId)
                .map(ProductMapper.INSTANCE::productToProductFoeCollabResDto);
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
                .map(name -> new ProductImage(type, endpoint + this.productContainer + "/" + name))
                .peek(this.imageRepository::save)
                .toArray(ProductImage[]::new)
        );

    }

    //===================Utils Methods====================
    //====================================================
    private final Supplier<ProductNotFoundException> handlerProductNotFound =
            () -> new ProductNotFoundException(MessagesUtils.getMessage(MessageConstant.Product.NOT_FOUND));

    private final Supplier<CategoryNotFoundException> handlerCategoryNotFound =
            () -> new CategoryNotFoundException(MessagesUtils.getMessage(MessageConstant.Category.NOT_FOUND));

    private final Supplier<AccountNotFoundException> handlerAccountCreatorNotFound = () -> new AccountNotFoundException(MessagesUtils.getMessage(MessageConstant.Account.CREATOR_NOT_FOUND));

    private final Supplier<AccountNotFoundException> handlerAccountNotFound = () -> new AccountNotFoundException(MessagesUtils.getMessage(MessageConstant.Account.NOT_FOUND));

    private final Supplier<ProductInvalidException> handlerProductInvalidException = () -> new ProductInvalidException(MessagesUtils.getMessage(MessageConstant.Product.INVALID));

    private PageImplResDto<ProductResDto> getProductResDtoPageImplResDto(Integer pageNumber, Integer pageSize, Specification<Product> search) {
        pageSize = Objects.isNull(pageSize) || (pageSize <= 1) ? 50 : pageSize;
        pageNumber = Objects.isNull(pageNumber) || (pageNumber <= 1) ? 1 : pageNumber;

        Page<Product> page = this.productRepository
                .findAll(search, PageRequest.of(pageNumber - 1, pageSize, Sort.by(Product_.CREATE_DATE).descending()));

        List<ProductResDto> data = page.stream()
                .map(MapperDTO.INSTANCE::toProductResDto).collect(toList());

        return new PageImplResDto<>(
                data, page.getNumber() + 1, data.size(),
                page.getTotalElements(), page.getTotalPages(),
                page.isFirst(), page.isLast());
    }


    //TODO ASYNC
    private Product handleImage(List<MultipartFile> typeImages, List<MultipartFile> certificate, Product entity) {
        if (Objects.nonNull(typeImages) && !typeImages.isEmpty()) {
            saveProductImageEntity(typeImages, entity.getId(), ProductImageType.NORMAL)
                    .ifPresent(entity::addProductImage);
        }
        if (Objects.nonNull(certificate) && !certificate.isEmpty()) {
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

            imageMap.forEach((key, value) ->
                    CompletableFuture.runAsync(() -> blobUploadImages.azureProductStorageHandler(key, value)));

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

    private void clearCache() {
        requireNonNull(cacheManager.getCache(ALL_PRODUCT_BY_ENTERPRISE)).clear();
        requireNonNull(cacheManager.getCache(PRODUCT_FOR_COLLABORATOR)).clear();
        requireNonNull(cacheManager.getCache(PRODUCT_BY_ID_FOR_ENTERPRISE)).clear();
        requireNonNull(cacheManager.getCache(PRODUCT_BY_ID)).clear();
        requireNonNull(cacheManager.getCache(LIST_PRODUCT_COUNTER_ORDER)).clear();
        requireNonNull(cacheManager.getCache(LIST_PRODUCT_NO_REGISTERED_FOR_COLLABORATOR)).clear();
        requireNonNull(cacheManager.getCache(LIST_PRODUCT_REGISTERED_FOR_COLLABORATOR)).clear();
        requireNonNull(cacheManager.getCache(LIST_PRODUCT_FOR_COLLABORATOR)).clear();
        requireNonNull(cacheManager.getCache(PRODUCT_ID_FOR_COLLABORATOR)).clear();
    }
}
