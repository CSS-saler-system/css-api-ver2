package com.springframework.csscapstone.css_business.services.impl;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.css_business.model_dto.custom.Image;
import com.springframework.csscapstone.css_business.services.CategoryService;
import com.springframework.csscapstone.css_business.model_dto.basic.CategoryDto;
import com.springframework.csscapstone.css_business.model_dto.custom.creator_model.CategoryCreatorDto;
import com.springframework.csscapstone.css_business.model_dto.custom.return_model.category.CategoryReturnDto;
import com.springframework.csscapstone.css_business.model_dto.custom.search_model.CategorySearchDto;
import com.springframework.csscapstone.css_business.model_dto.custom.update_model.CategoryUpdaterDto;
import com.springframework.csscapstone.css_data.domain.*;
import com.springframework.csscapstone.css_data.repositories.AccountRepository;
import com.springframework.csscapstone.css_data.repositories.CategoryRepository;
import com.springframework.csscapstone.css_data.repositories.ProductImageRepository;
import com.springframework.csscapstone.css_data.repositories.ProductRepository;
import com.springframework.csscapstone.css_data.status.CategoryStatus;
import com.springframework.csscapstone.css_data.status.ProductImageType;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.category_exception.CategoryInvalidException;
import com.springframework.csscapstone.utils.mapper_utils.MapperDTO;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final EntityManager em;

    @Override
    public List<CategoryDto> findCategories(CategorySearchDto dto) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Category> query = builder.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);

        List<Predicate> predicates = Arrays.asList(
                builder.like(root.get(Category_.CATEGORY_NAME), dto.getCategoryName()),
                builder.equal(root.get(Category_.STATUS), dto.getStatus())
        );
        CriteriaQuery<Category> processQuery = query.where(builder.and(predicates.toArray(new Predicate[0])));

        return em.createQuery(processQuery).getResultList()
                .stream().map(MapperDTO.INSTANCE::toCategoryDto)
                .collect(toList());
    }

    @Override
    @Cacheable(cacheNames = "cache_product_of_category")
    public CategoryReturnDto findCategoryById(UUID id) throws EntityNotFoundException {
        //get list category
        Category _category = categoryRepository.findById(id).orElseThrow(categoryErrorNotFound());

        //get list product
        List<CategoryReturnDto._ProductDto> products = this.productRepository
                .findProductByCategory(_category)
                .stream().map(product -> new CategoryReturnDto._ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getBrand(),
                        product.getWeight(),
                        product.getShortDescription(),
                        product.getDescription(),
                        product.getQuantityInStock(),
                        product.getPrice(),
                        product.getPointSale(),
                        product.getProductStatus(),
                        getProductImages(product, ProductImageType.NORMAL),
                        getProductImages(product, ProductImageType.CERTIFICATION))
                ).collect(toList());

        return new CategoryReturnDto(_category.getId(), _category.getCategoryName(), _category.getStatus(), products);

    }

    /**
     * Get List Image of product by type
     * @param product
     * @param type
     * @return
     */
    private List<Image> getProductImages(Product product, ProductImageType type) {
        return this.productImageRepository
                .findProductImageByProduct(product)
                .stream().filter(x ->x.getType().equals(type))
                .map(x -> new Image(x.getId(), x.getType(), x.getPath()))
                .collect(toList());
    }

    @Transactional

    @Override
    public UUID createCategory(CategoryCreatorDto dto) throws CategoryInvalidException {

        if (dto.getAccountId() == null) {
            throw new CategoryInvalidException(MessagesUtils.getMessage(MessageConstant.Category.INVALID));
        }

        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(categoryErrorInvalid());

        return Optional.of(new Category())
                .map(x -> createCategory(dto, x, account))
                .map(Category::getId)
                .orElseThrow(categoryErrorInvalid());
    }
    @Transactional
    @Override
    public UUID updateCategory(CategoryUpdaterDto dto) throws CategoryInvalidException {
        Category category = this.categoryRepository.findById(dto.getId())
                .orElseThrow(categoryErrorInvalid());

        Category _result = Optional.of(category)
                .map(x -> x.setCategoryName(dto.getName()).setStatus(dto.getStatus()))
                .orElseThrow(categoryErrorInvalid());

        return _result.getId();
    }
    @Transactional
    @Override
    public void deleteCategory(UUID id) throws EntityNotFoundException {
        this.categoryRepository.findById(id)
                .map(x -> x.setStatus(CategoryStatus.DISABLE))
                .orElseThrow(categoryErrorNotFound());
    }

    //    =============================================
    //    ==================Utils======================
    private Supplier<EntityNotFoundException> categoryErrorNotFound() {
        return () -> new EntityNotFoundException(MessagesUtils.getMessage(MessageConstant.Category.NOT_FOUND));
    }

    //    =============================================
    //    ==================Utils======================
    private Supplier<CategoryInvalidException> categoryErrorInvalid() {
        return () -> new CategoryInvalidException(MessagesUtils.getMessage(MessageConstant.Category.INVALID));
    }

    //    =============================================
    //    ==================Utils======================
    private Category createCategory(CategoryCreatorDto dto, Category x, Account account) {
        x.setCategoryName(dto.getCategoryName())
                .setStatus(dto.getStatus())
                .addAccount(account);
        this.categoryRepository.save(x);
        this.accountRepository.save(account);
        return x;
    }

}
