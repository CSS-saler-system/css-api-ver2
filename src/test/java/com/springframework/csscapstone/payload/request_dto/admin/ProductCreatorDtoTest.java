package com.springframework.csscapstone.payload.request_dto.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductCreatorDtoTest {
    @Test
    void ProductCreatorToJsonTest() {
        ProductCreatorDto json = new ProductCreatorDto(
                UUID.fromString("e283b676-6614-4088-83d4-74bd370c781c"),
                UUID.fromString("687c91a0-0012-c04d-8e73-17f616d4f8ec"),
                "Ductlmse",
                "Silicon Velley",
                "This is Robot comes from future, 21th", "The best robot", 13L, 13.0, 13.0);
        try {
            System.out.println(json);
            ObjectMapper mapperJson = new ObjectMapper();
            String jsonString = mapperJson.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            System.out.println(jsonString);

            ProductCreatorDto productCreatorDto = mapperJson.readValue(jsonString, ProductCreatorDto.class);
            System.out.println(productCreatorDto);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}