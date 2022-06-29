package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Category;
import com.springframework.csscapstone.data.domain.Product;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    String CATEGORY_NAME = "tuple_categories";
    String PRODUCT_LIST = "tuple_products";
    @Transactional(readOnly = true)
    List<Product> findProductByCategory(Category category);
    @Transactional(readOnly = true)
    @Query("SELECT p FROM Product p WHERE p.name LIKE CONCAT('%',:name,'%')")
    List<Product> findProductByNameLike(@Param("name") String name);

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT p " +
            "FROM Product p " +
            "LEFT JOIN FETCH p.image " +
            "WHERE p.id = :id")
    Optional<Product> findProductFetchJoinImageAndCategoryAccountById(@Param("id") UUID id);

    @Query("SELECT p.category.categoryName AS "+ CATEGORY_NAME + ", " +
            "p AS " + PRODUCT_LIST + " " +
            "FROM Product p " +
            "WHERE p.account.id = :enterpriseId " +
            "GROUP BY p.category, p")
    List<Tuple> getProductByCategoryAndEnterprise(UUID enterpriseId);


}