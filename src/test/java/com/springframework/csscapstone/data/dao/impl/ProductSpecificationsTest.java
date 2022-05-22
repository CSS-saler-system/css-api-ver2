package com.springframework.csscapstone.data.dao.impl;

import com.springframework.csscapstone.data.dao.specifications.ProductSpecifications;
import com.springframework.csscapstone.data.domain.Product;
import com.springframework.csscapstone.data.repositories.ProductRepository;
import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.basic.ProductDto;
import com.springframework.csscapstone.payload.request_dto.admin.ProductSearchListDto;
import com.springframework.csscapstone.utils.mapper_utils.MapperDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.springframework.csscapstone.data.dao.specifications.ProductSpecifications.nameContains;
import static java.util.stream.Collectors.toList;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles(value = "test")
class ProductSpecificationsTest {

    @Autowired
    ProductRepository productRepository;

    ProductSearchListDto dto;
    @BeforeEach
    void setUp() {
        dto = new ProductSearchListDto("c", null, 1.0,
                1L, 100.0, 1.1,
                ProductStatus.ACTIVE);
    }

    @Test
    void nameContainsTest() {
        Specification<Product> search = Specification
                .where(dto.getName() == null ? null : nameContains(dto.getName()))
                .and(dto.getBrand() == null ? null : ProductSpecifications.brandContains(dto.getBrand()));
        List<ProductDto> collect = productRepository.findAll(search)
                .stream().map(MapperDTO.INSTANCE::toProductDto).collect(toList());
        System.out.println(collect);
    }

}