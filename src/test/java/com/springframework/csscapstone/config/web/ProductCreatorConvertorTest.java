package com.springframework.csscapstone.config.web;

import com.springframework.csscapstone.payload.request_dto.admin.ProductCreatorDto;
import com.springframework.csscapstone.utils.mapper_utils.ProductCreatorConvertor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles(value = "test")
@ComponentScan(basePackages = {"com.springframework.csscapstone.config.web"})
class ProductCreatorConvertorTest {
    @Autowired
    ProductCreatorConvertor convertor;

    @Test
    void convert() {
        String json = "{\n" +
                "\"creatorAccountId\" : null,\n" +
                "\"categoryId\" : null,\n" +
                "\"name\" : \"\",\n" +
                "\"brand\" : \"\",\n" +
                "\"shortDescription\" : \"\",\n" +
                "\"description\" : \"\",\n" +
                "\"quantity\" : 0,\n" +
                "\"price\" : 0.0,\n" +
                "\"pointSale\" : 13.0\n" +
                "}";
        ProductCreatorDto dto = convertor.convert(json);
        System.out.println("This is dot: " + dto);
    }
}