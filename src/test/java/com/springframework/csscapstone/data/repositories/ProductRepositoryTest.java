package com.springframework.csscapstone.data.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.data.domain.Product;
import com.springframework.csscapstone.data.domain.ProductImage;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResponseDto;
import com.springframework.csscapstone.utils.mapper_utils.MapperDTO;
import org.hibernate.annotations.QueryHints;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@ActiveProfiles(value = "test")
@AutoConfigureTestDatabase(replace = NONE)
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductImageRepository imageRepository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findProductByCategory() {
    }

    @Test
    void findProductByNameLike() {
    }

    @Test
    void findProductFetchJoinImageAndCategoryAccountById() throws JsonProcessingException {
//        Optional<ProductImage> byId = imageRepository.findById(1L);
        List<Product> product;
        product = entityManager
                .createQuery("" +
                        "SELECT DISTINCT p " +
                        "FROM Product p " +
                        "LEFT JOIN FETCH p.image " +
                        "WHERE p.id =:id", Product.class)
//                .setParameter("id",byId.get().getProduct().getId())
                .setParameter("id", UUID.fromString("a91c127d-980d-420f-94e7-868777701946"))
                .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
                .getResultList();
        ProductResponseDto productResponseDto = MapperDTO.INSTANCE.toProductResponseDto(product.get(0));
        System.out.println(new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(productResponseDto));


    }
}