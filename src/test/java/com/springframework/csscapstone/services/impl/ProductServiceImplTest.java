package com.springframework.csscapstone.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.payload.basic.ProductDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResponse;
import com.springframework.csscapstone.payload.response_dto.PageProductDto;
import com.springframework.csscapstone.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(value = "test")
class ProductServiceImplTest {
    @Autowired
    ProductService service;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllProductTest() throws JsonProcessingException {

    }
}