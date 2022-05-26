package com.springframework.csscapstone.payload.request_dto.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AccountCreatorDtoJsonTest {

    @Test
    void accountCreatorDtoToJsonTest() throws JsonProcessingException {
        String json = new ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValueAsString(
                        new AccountCreatorDto(
                                "", LocalDate.now(), "", "",
                                "", "", "", true, ""));
        System.out.println(json);
    }
}