package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Category;
import com.springframework.csscapstone.data.domain.Category_;
import com.springframework.csscapstone.data.domain.Product;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.data.repositories.CategoryRepository;
import com.springframework.csscapstone.data.repositories.ProductImageRepository;
import com.springframework.csscapstone.data.repositories.ProductRepository;
import com.springframework.csscapstone.data.status.CategoryStatus;
import com.springframework.csscapstone.data.status.ProductImageType;
import com.springframework.csscapstone.payload.basic.CategoryBasicDto;
import com.springframework.csscapstone.payload.request_dto.admin.CategoryCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.admin.CategorySearchReqDto;
import com.springframework.csscapstone.payload.request_dto.admin.CategoryUpdaterReqDto;
import com.springframework.csscapstone.payload.request_dto.admin.ProductImageReqDto;
import com.springframework.csscapstone.payload.response_dto.admin.CategoryResDto;
import com.springframework.csscapstone.services.CategoryService;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.category_exception.CategoryInvalidException;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.MapperDTO;
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
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public List<CategoryResDto> getListCategory() {
        return this.categoryRepository
                .findAll()
                .stream()
                .filter(cate -> cate.getStatus().equals(CategoryStatus.ACTIVE))
                .map(MapperDTO.INSTANCE::toCategoryResDto)
                .collect(toList());
    }

    @Override
    public List<CategoryResDto> findCategories(CategorySearchReqDto dto) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Category> query = builder.createQuery(Category.class);
        Root<Category> root = query.from(Category.class);

        List<Predicate> predicates = Arrays.asList(
                builder.like(root.get(Category_.CATEGORY_NAME), dto.getCategoryName()),
                builder.equal(root.get(Category_.STATUS), dto.getStatus())
        );
        CriteriaQuery<Category> processQuery = query.where(builder.and(predicates.toArray(new Predicate[0])));

        return em.createQuery(processQuery).getResultList()
                .stream().map(MapperDTO.INSTANCE::toCategoryResDto)
                .collect(toList());
    }

    @Override
    @Cacheable(cacheNames = "cache_product_of_category")
    public CategoryResDto findCategoryById(UUID id) throws EntityNotFoundException {
        //get list category
        Category _category = categoryRepository.findById(id).orElseThrow(categoryErrorNotFound());

        //get list product
        List<CategoryResDto.ProductInnerCategoryResDto> products = _category.getProducts()
                .stream().map(product -> new CategoryResDto.ProductInnerCategoryResDto(
                        product.getId(),
                        product.getName(),
                        product.getBrand(),
                        product.getShortDescription(),
                        product.getDescription(),
//                        product.getQuantityInStock(),
                        product.getPrice(),
                        product.getPointSale(),
                        product.getProductStatus(),
                        getProductImages(product, ProductImageType.NORMAL)
                )).collect(toList());

        return new CategoryResDto(_category.getId(), _category.getCategoryName(), _category.getStatus(), products);

    }

    /**
     * TODO NEED MODIFIED
     * Get List Image of product by type
     */
    private List<ProductImageReqDto> getProductImages(Product product, ProductImageType type) {
        return this.productImageRepository
                .findProductImageByProduct(product)
                .stream().filter(x ->x.getType().equals(type))
                .map(x -> new ProductImageReqDto(x.getId(), x.getPath()))
                .collect(toList());
    }

    @Transactional
    @Override
    public UUID createCategory(CategoryCreatorReqDto dto) throws CategoryInvalidException {

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
    public UUID updateCategory(CategoryUpdaterReqDto dto) throws CategoryInvalidException {
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

    @Override
    public List<CategoryResDto> getListCategoryByEnterprise(UUID enterpriseId) {
//        Map<Category, List<Product>> result = this.categoryRepository
//                .findAll()
//                .stream().collect(Collectors.toMap(
//                        cate -> cate,
//                        cate -> cate.getProducts()
//                                .stream()
//                                .filter(pro -> pro.getAccount().getId().equals(enterpriseId))
//                                .collect(toList())));
//        return result.entrySet().stream().map(entry -> new CategoryResDto(entry.getKey(), entry.getValue().stream()
//                .map(MapperDTO.INSTANCE::CategoryInner).collect(toList())));
        return null;
    }

    @Override
    public List<CategoryBasicDto> getCategoryBasicResDto() {
        List<CategoryBasicDto> result = this.categoryRepository
                .findAll()
                .stream()
                .filter(cate -> cate.getStatus().equals(CategoryStatus.ACTIVE))
                .map(MapperDTO.INSTANCE::toCategoryBasicDto)
                .collect(toList());
        return result;
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
    private Category createCategory(CategoryCreatorReqDto dto, Category x, Account account) {
        x.setCategoryName(dto.getCategoryName())
                .setStatus(dto.getStatus());
        this.categoryRepository.save(x);
        this.accountRepository.save(account);
        return x;
    }

}
