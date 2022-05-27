package com.springframework.csscapstone.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.payload.response_dto.PageAccountDto;
import com.springframework.csscapstone.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(value = "test")
class AccountServiceImplTest {
    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Test
    void getAllDto() throws JsonProcessingException {
        //lazy loading
        PageAccountDto allDto = accountService.getAllDto("", "", "", "", null, null);
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(allDto));
    }

    @Test
    void findAllAccount() {
        //Lazy load
        this.accountRepository.findAll()
//                .stream()
//                .map(Account::getId)
                .forEach(System.out::println);
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