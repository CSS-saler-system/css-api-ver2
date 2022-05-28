package com.springframework.csscapstone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.payload.request_dto.admin.ProductCreatorDto;
import com.springframework.csscapstone.payload.response_dto.PageAccountDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResponseDto;
import com.springframework.csscapstone.services.AccountService;
import com.springframework.csscapstone.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles(value = "test")
class CssCapstoneApplicationTest {

    @Autowired
    AccountService accountService;

    @Autowired
    ProductService productService;



    @Test
    void getAllAccountTest() {
    }

    @Test
    void getProductByAccountTest() throws AccountNotFoundException {
        productService
                .findProductByIdAccount(UUID.fromString("939ad7c1-9dfb-0d4c-b8d6-ffd3b7643fe3"))
                .stream()
                .map(x -> {
                    try {
                        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(x);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .forEach(System.out::println);
    }

    @Test
    void getProductByIdTest() throws JsonProcessingException {
        ProductResponseDto result = productService.findById(UUID.fromString("f690e575-13d0-8942-b6e2-53601e98433e"));
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(result));
    }

    @Test
    void printJsonProductDtoTest() throws JsonProcessingException {
        ProductCreatorDto jsonObject = new ProductCreatorDto(null, null, "",
                "", "", "",
                0L, 0.0, 13.0);
        String s = new ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValueAsString(jsonObject);
        System.out.println(s);
    }

    @Test
    void createProductServiceTest() {

//        this.productService.createProduct()
    }
}