package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Category;
import com.springframework.csscapstone.data.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    String CATEGORY_NAME = "tuple_categories";
    String PRODUCT_LIST = "tuple_products";

    List<Product> findProductByCategory(Category category);


    @Query("SELECT p FROM Product p WHERE p.name LIKE CONCAT('%',:name,'%')")
    List<Product> findProductByNameLike(@Param("name") String name);


    @Query("SELECT DISTINCT p " +
            "FROM Product p " +
            "LEFT JOIN FETCH p.image " +
            "WHERE p.id = :id")
    Optional<Product> findProductFetchJoinImageAndCategoryAccountById(@Param("id") UUID id);

    @Query("SELECT p.category.categoryName AS " + CATEGORY_NAME + ", " +
            "p AS " + PRODUCT_LIST + " " +
            "FROM Product p " +
            "WHERE p.account.id = :enterpriseId " +
            "GROUP BY p.category, p")
    List<Tuple> getProductByCategoryAndEnterprise(UUID enterpriseId);


    /**
     * UUID.fromString("5a741a45-664c-b140-873b-3e3b7cc4d04f"),
     * UUID.fromString("8b37872e-b974-4e04-a485-cedfee79a511"),
     */
    @Query(value =
            "SELECT p FROM Product p " +
                    "WHERE p IN (SELECT _p FROM Product _p " +
                                "JOIN _p.requestSellingProducts r " +
                                "WHERE r.account.id = :collaboratorId)" +
                    "AND p.account.id = :enterpriseId " +
                    "AND p.productStatus = 'ACTIVE' ")
    Page<Product> getAllProductRegister(
            @Param("collaboratorId") UUID collaboratorId,
            @Param("enterpriseId") UUID enterpriseId,
            Pageable pageable);

    //    @Query(value =
//            "SELECT distinct p FROM Product p " +
//                    "JOIN p.requestSellingProducts r " +
//                    "WHERE p.account.id = :enterpriseId " +
//                    "AND p.productStatus = 'ACTIVE' " +
//                    "AND r.account.id = :collaboratorId " +
//                    "AND NOT r.requestStatus = 'REGISTERED' " +
//                    "AND NOT r.requestStatus = 'CREATED'")
//    Page<Product> getAllProductNotRegister(
//            @Param("collaboratorId") UUID collaboratorId,
//            @Param("enterpriseId") UUID enterpriseId,
//            Pageable pageable
//    );
    @Query(value =
            "SELECT p FROM Product p " +
                    "WHERE p NOT IN (SELECT _p FROM Product _p " +
                    "JOIN _p.requestSellingProducts r " +
                    "WHERE r.account.id = :collaboratorId)" +
                    "AND p.account.id = :enterpriseId " +
                    "AND p.productStatus = 'ACTIVE' ")
    Page<Product> getAllProductNotRegister(
            @Param("collaboratorId") UUID collaboratorId,
            @Param("enterpriseId") UUID enterpriseId,
            Pageable pageable
    );

}
