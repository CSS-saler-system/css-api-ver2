package com.springframework.csscapstone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.payload.response_dto.PageAccountDto;
import com.springframework.csscapstone.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles(value = "test")
class CssCapstoneApplicationTest {

    @Autowired
    AccountService accountService;

    @Test
    void getAllHavingEnterpriseRoleTest() {
        accountService.getAllHavingEnterpriseRole(0, 100)
                .getData().forEach(System.out::println);
    }

    @Test
    void getAllDto() throws JsonProcessingException {
        //lazy loading
        PageAccountDto allDto = accountService.getAccountDto("", "", "", "", null, null);
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(allDto));
    }

    @Test
    void findAllAccount() throws JsonProcessingException {
        PageAccountDto accountDto = accountService.getAccountDto(
                null, null, null, null, 100,
                null);
        String json = new ObjectMapper().writerWithDefaultPrettyPrinter()
                .writeValueAsString(accountDto);
        System.out.println(json);
    }

    @Test
    void getById() {
//        accountService.getById()
    }

    @Test
    void registerAccount() {
    }

    @Test
    void updateAccount() {
    }

    @Test
    void disableAccount() {
    }

    @Test
    void getAllHavingEnterpriseRole() {
    }
}