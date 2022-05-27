package com.springframework.csscapstone.payload.sharing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountUpdaterDtoTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void jsonGetDtoTest() throws JsonProcessingException {

        AccountUpdaterDto accountUpdaterDto = new AccountUpdaterDto(null, "", null, "", "", "", ""
                , null);
        String json = new ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValueAsString(accountUpdaterDto);
        System.out.println(json);
    }
}