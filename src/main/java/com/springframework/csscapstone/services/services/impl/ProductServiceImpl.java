package com.springframework.csscapstone.services.services.impl;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.services.services.ProductService;
import com.springframework.csscapstone.services.model_dto.basic.ProductDto;
import com.springframework.csscapstone.services.model_dto.custom.creator_model.ProductCreatorDto;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Product;
import com.springframework.csscapstone.data.domain.Product_;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.data.repositories.ProductRepository;
import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductInvalidException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.MapperDTO;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.security.auth.login.AccountNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repositories;
    private final AccountRepository accountRepository;
    private final EntityManager em;

    @Override
    public List<ProductDto> findAllProduct(
            String name, String brand, Double weight,
            String shortDescription, String description,
            Long inStock, Double price, Double pointSale,
            ProductStatus productStatus) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);

        List<Predicate> predicates = Arrays.asList(
                builder.like(root.get(Product_.NAME), name),
                builder.like(root.get(Product_.BRAND), brand),
                builder.like(root.get(Product_.SHORT_DESCRIPTION), shortDescription),
                builder.like(root.get(Product_.DESCRIPTION), description),
                builder.greaterThanOrEqualTo(root.get(Product_.WEIGHT), weight),
                builder.greaterThanOrEqualTo(root.get(Product_.QUANTITY_IN_STOCK), inStock),
                builder.greaterThanOrEqualTo(root.get(Product_.PRICE), price),
                builder.greaterThanOrEqualTo(root.get(Product_.POINT_SALE), pointSale),
                builder.equal(root.get(Product_.PRODUCT_STATUS), productStatus)
        );

        CriteriaQuery<Product> processQuery = query.where(builder.and(predicates.toArray(new Predicate[0])));

        return em.createQuery(processQuery)
                .getResultList()
                .stream().map(MapperDTO.INSTANCE::toProductDto)
                .collect(Collectors.toList());
    }

    /**
     * todo find product by account
     * @param accountId
     * @return
     * @throws AccountNotFoundException
     */
    @Override
    public List<ProductDto> findByIdAccount(UUID accountId) throws AccountNotFoundException {
        Account account = this.accountRepository.findById(accountId).orElseThrow(accountNotFoundException());
        return account.getProducts().stream().map(MapperDTO.INSTANCE::toProductDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto findById(UUID id) throws ProductNotFoundException {
        return repositories.findById(id)
                .map(MapperDTO.INSTANCE::toProductDto)
                .orElseThrow(handlerNotFoundProduct());
    }
    @Transactional

    @Override
    public UUID createProduct(ProductCreatorDto dto) throws ProductInvalidException {

        if (dto.getAccountId() == null) {
            throw new ProductInvalidException(MessagesUtils.getMessage(MessageConstant.Product.INVALID));
        }

        Account account = accountRepository
                .findById(dto.getAccountId())
                .orElseThrow(handlerInvalidFormat());

        Product product = Optional.of(new Product())
                .map(x -> toEntityFromCreator(dto, x, account))
                .orElseThrow(handlerInvalidFormat());

        Product creatorProduct = repositories.save(product);
        accountRepository.save(account);
        return creatorProduct.getId();
    }

    @Transactional
    @Override
    public UUID updateProductDto(ProductDto dto) throws ProductNotFoundException, ProductInvalidException {
        if (dto.getId() == null) {
            throw new ProductInvalidException(MessagesUtils.getMessage(MessageConstant.Product.INVALID));
        }

        Product entity = this.repositories
                .findById(dto.getId())
                .orElseThrow(handlerNotFoundProduct());

        entity.setName(dto.getName())
                .setBrand(dto.getBrand())
                .setDescription(dto.getDescription())
                .setShortDescription(dto.getShortDescription())
                .setPointSale(dto.getPointSale())
                .setPrice(dto.getPrice())
                .setWeight(dto.getWeight())
                .setQuantityInStock(dto.getQuantityInStock())
                .setProductStatus(dto.getProductStatus());

        return entity.getId();
    }
    @Transactional
    @Override
    public void disableProduct(UUID id) {
        this.repositories.findById(id)
                .ifPresent(x -> {
                    x.setProductStatus(ProductStatus.DISABLE);
                    this.repositories.save(x);
                });
    }

    //===================Utils Methods====================
    //====================================================
    private Product toEntityFromCreator(ProductCreatorDto dto, Product x, Account account) {
        x.setName(dto.getName())
                .setBrand(dto.getBrand())
                .setWeight(dto.getWeight())
                .setDescription(dto.getDescription())
                .setShortDescription(dto.getShortDescription())
                .setQuantityInStock(dto.getQuantity())
                .setPrice(dto.getPrice())
                .setPointSale(dto.getPointSale())
                .setProductStatus(dto.getStatus())
                //==== bi-direction: account <=> product
                .addAccount(account);
        return x;
    }

    //===================Utils Methods====================
    //====================================================
    private Supplier<ProductNotFoundException> handlerNotFoundProduct() {
        return () -> new ProductNotFoundException(MessageConstant.Product.NOT_FOUND);
    }

    //===================Utils Methods====================
    //====================================================
    private Supplier<ProductInvalidException> handlerInvalidFormat() {
        return () -> new ProductInvalidException(MessagesUtils.getMessage(MessageConstant.Product.INVALID));
    }

    private Supplier<AccountNotFoundException> accountNotFoundException() {
        return () -> new AccountNotFoundException(MessagesUtils.getMessage(MessageConstant.Account.NOT_FOUND));
    }
}
