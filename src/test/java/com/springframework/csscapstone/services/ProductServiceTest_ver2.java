package com.springframework.csscapstone.services;

import com.springframework.csscapstone.payload.request_dto.admin.ProductCreatorDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@PropertySource("classpath:application-test.properties")
class ProductServiceTest_ver2 {

    @Autowired
    ProductService productService;
    @BeforeEach
    void setUp() {
    }

    @Test
    void createProduct() {

    }
}