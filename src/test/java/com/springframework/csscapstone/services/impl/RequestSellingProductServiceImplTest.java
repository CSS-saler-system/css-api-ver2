package com.springframework.csscapstone.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.payload.response_dto.enterprise.RequestSellingProductDto;
import com.springframework.csscapstone.services.RequestSellingProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(value = "test")
class RequestSellingProductServiceImplTest {
    @Autowired
    RequestSellingProductService requestSellingProductService;
    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllRequestRequest() {

        List<RequestSellingProductDto> allRequestRequest =
                requestSellingProductService.getAllRequest();

        allRequestRequest.forEach(x -> {
            try {
                String json = new ObjectMapper().writerWithDefaultPrettyPrinter()
                        .writeValueAsString(x);
                System.out.println(json);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}