package com.springframework.csscapstone.services.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.dao.specifications.ProductSpecifications;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Category;
import com.springframework.csscapstone.data.domain.Product;
import com.springframework.csscapstone.data.domain.ProductImage;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.data.repositories.CategoryRepository;
import com.springframework.csscapstone.data.repositories.ProductImageRepository;
import com.springframework.csscapstone.data.repositories.ProductRepository;
import com.springframework.csscapstone.data.status.ProductImageType;
import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.basic.ProductDto;
import com.springframework.csscapstone.payload.request_dto.admin.ProductCreatorDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResponse;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResponseDto;
import com.springframework.csscapstone.services.ProductService;
import com.springframework.csscapstone.utils.exception_utils.category_exception.CategoryNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductInvalidException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.MapperDTO;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@PropertySource(value = "classpath:application-storage.properties")
public class ProductServiceImpl implements ProductService {
    @Value("${product_image_container}")
    private String productContainer;

    @Value("${endpoint}")
    private String endpoint;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository imageRepository;

    @Override
    public PageImplResponse<ProductDto> findAllProduct(
            String name, String brand, Long inStock, Double minPrice, Double maxPrice,
            Double minPoint, Double maxPoint, ProductStatus productStatus,
            Integer pageNumber, Integer pageSize) {

        Specification<Product> search = Specification
                .where(Objects.isNull(name) ? null : ProductSpecifications.nameContains(name))
                .and(Objects.isNull(brand) ? null : ProductSpecifications.brandContains(brand))
                .and(Objects.isNull(minPrice) ? null : ProductSpecifications.priceGreaterThan(minPrice))
                .and(Objects.isNull(maxPrice) ? null : ProductSpecifications.priceLessThan(maxPrice))
                .and(Objects.isNull(minPoint) ? null : ProductSpecifications.pointGreaterThan(minPoint))
                .and(Objects.isNull(maxPoint) ? null : ProductSpecifications.pointLessThan(maxPoint))
                .and(Objects.isNull(productStatus) ? null : ProductSpecifications.statusEquals(productStatus));

        pageSize = Objects.isNull(pageSize) || (pageSize <= 0) ? 10 : pageSize;
        pageNumber = Objects.isNull(pageNumber) || (pageNumber <= 1) ? 1 : pageNumber;

        Page<Product> page = this.productRepository.findAll(search, PageRequest.of(pageNumber - 1, pageSize));

        List<ProductDto> data = page.stream().map(MapperDTO.INSTANCE::toProductDto).collect(toList());

        return new PageImplResponse<>(
                data, page.getNumber() + 1, page.getSize(),
                page.getTotalElements(), page.getTotalPages(),
                page.isFirst(), page.isLast());
    }

    /**
     * todo find product by account <Completed></>
     */
    @Override
    public List<ProductDto> findProductByIdAccount(UUID accountId) throws AccountNotFoundException {
        Account account = this.accountRepository.findById(accountId).orElseThrow(handlerAccountNotFound());
        return account.getProducts().stream().map(MapperDTO.INSTANCE::toProductDto).collect(toList());
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

        if (Objects.isNull(dto.getCategoryId())) throw handlerCategoryNotFound().get();
        if (Objects.isNull(dto.getCreatorAccountId())) throw handlerAccountCreatorNotFound().get();

        Account account = accountRepository
                .findById(dto.getCreatorAccountId())
                .orElseThrow(handlerAccountCreatorNotFound());

        Category category = categoryRepository
                .findById(dto.getCategoryId())
                .orElseThrow(handlerCategoryNotFound());

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


        Product creatorProduct = productRepository.save(newProduct);

        handleImage(typeImages, creatorProduct, ProductImageType.NORMAL);
        handleImage(certificationImages, creatorProduct, ProductImageType.CERTIFICATION);

        return creatorProduct.getId();
    }

    /**
     * this method todo <>
     * Add image to product
     * Upload image to aure storage
     * </>
     *
     * @param typeImages
     * @param creatorProduct
     * @param type
     * @throws IOException
     */
    private void handleImage(List<MultipartFile> typeImages, Product creatorProduct, ProductImageType type) throws IOException {
        String prefix_image = endpoint + productContainer + creatorProduct.getId() + "/";

        //create productImage
        Map<String, MultipartFile> image = typeImages
                .stream()
                .collect(Collectors.toMap(x -> prefix_image + x.getOriginalFilename(), x -> x));

        //set image into product
        ProductImage[] productImages = image.keySet().stream()
                .map(x -> new ProductImage(type, x))
                .toArray(ProductImage[]::new);
        creatorProduct.addProductImage(productImages);

        //deploy
        for (Map.Entry<String, MultipartFile> entry : image.entrySet()) {

            BlobContainerClient blobContainer = new BlobContainerClientBuilder()
                    .containerName(productContainer)
                    .connectionString("DefaultEndpointsProtocol=https;AccountName=csssalersystem;AccountKey=jCb20BfSP2CkB1IduJlPAxcQWX+GgwrBp+aobpk5ggaUpKa2dSGf9iSH4QggdFb9Nwjm/o+un2X3ScNdjrpovA==;EndpointSuffix=core.windows.net")
                    .buildClient();

            BlobClient blobClient = blobContainer.getBlobClient(entry.getKey());

            blobClient.upload(
                    entry.getValue().getInputStream(),
                    entry.getValue().getSize(), true);
        }
        this.imageRepository.saveAll(Arrays.asList(productImages));
        this.productRepository.save(creatorProduct);
    }

    @Transactional
    @Override
    public UUID updateProductDto(ProductDto dto) throws ProductNotFoundException, ProductInvalidException {
        if (dto.getId() == null) {
            throw new ProductInvalidException(MessagesUtils.getMessage(MessageConstant.Product.INVALID));
        }

        Product entity = this.productRepository
                .findById(dto.getId())
                .orElseThrow(handlerProductNotFound());

        entity.setName(dto.getName())
                .setBrand(dto.getBrand())
                .setDescription(dto.getDescription())
                .setShortDescription(dto.getShortDescription())
                .setPointSale(dto.getPointSale())
                .setPrice(dto.getPrice())
                .setQuantityInStock(dto.getQuantityInStock())
                .setProductStatus(dto.getProductStatus());

        return entity.getId();
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
}
