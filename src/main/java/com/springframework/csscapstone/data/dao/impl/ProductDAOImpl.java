package com.springframework.csscapstone.data.dao.impl;

import com.springframework.csscapstone.data.dao.ProductDAO;
import com.springframework.csscapstone.controller.domain.Product;
import com.springframework.csscapstone.data.domain.Product_;
import com.springframework.csscapstone.data.status.ProductStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO {
    private final EntityManager em;

    @Override
    public List<Product> searchProduct(String name, String brand, Double weight,
                                       String shortDescription, String description,
                                       Long inStock, Double price, Double pointSale,
                                       ProductStatus productStatus) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);

        Predicate predicate = builder.conjunction();

        predicate = builder.and(predicate, builder.like(root.get(Product_.NAME), name));
        predicate = builder.and(predicate, builder.like(root.get(Product_.BRAND), brand));
        predicate = builder.and(predicate, builder.like(root.get(Product_.SHORT_DESCRIPTION), shortDescription));
        predicate = builder.and(predicate, builder.like(root.get(Product_.DESCRIPTION), description));
        predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get(Product_.WEIGHT), weight));
        predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get(Product_.QUANTITY_IN_STOCK), inStock));
        predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get(Product_.PRICE), price));
        predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get(Product_.POINT_SALE), pointSale));
        predicate = builder.and(predicate, builder.equal(root.get(Product_.PRODUCT_STATUS), productStatus));

        CriteriaQuery<Product> processQuery = query.where(predicate);

        return em.createQuery(processQuery).getResultList();
    }
}
