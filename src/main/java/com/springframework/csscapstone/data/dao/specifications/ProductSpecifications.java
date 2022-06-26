package com.springframework.csscapstone.data.dao.specifications;

import com.springframework.csscapstone.data.domain.Product;
import com.springframework.csscapstone.data.domain.Product_;
import com.springframework.csscapstone.data.status.ProductStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

import static com.springframework.csscapstone.data.dao.specifications.ContainsString.contains;

public final class ProductSpecifications {

    public static Specification<Product> enterpriseId(UUID uuid) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Product_.ID), uuid);
    }

    public static Specification<Product> nameContains(String searchName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), contains(searchName));
    }

    public static Specification<Product> brandContains(String brandSearch) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.BRAND), contains(brandSearch));
    }


    public static Specification<Product> inStockEquals(Long inStock) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Product_.QUANTITY_IN_STOCK), inStock);
    }

    public static Specification<Product> priceGreaterThan(double minPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .and(criteriaBuilder.greaterThan(root.get(Product_.PRICE), minPrice));
    }
    public static Specification<Product> priceLessThan(double maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .and(criteriaBuilder.lessThan(root.get(Product_.PRICE), maxPrice));
    }

    public static Specification<Product> pointGreaterThan(double minPoint) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .and(criteriaBuilder.greaterThan(root.get(Product_.POINT_SALE), minPoint));
    }

    public static Specification<Product> pointLessThan(double maxPoint) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .and(criteriaBuilder.greaterThan(root.get(Product_.POINT_SALE), maxPoint));
    }

    public static Specification<Product> statusEquals(ProductStatus status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get(Product_.PRODUCT_STATUS), status);
    }
}
