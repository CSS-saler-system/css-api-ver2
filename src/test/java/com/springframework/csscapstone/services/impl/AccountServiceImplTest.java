package com.springframework.csscapstone.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.payload.response_dto.PageAccountDto;
import com.springframework.csscapstone.services.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = NONE)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = NONE)
@SpringBootTest
@ActiveProfiles(value = "test")
//@ComponentScan(basePackages = "com.springframework.csscapstone")
class AccountServiceImplTest {
    @Autowired
    AccountService accountService;

    @Test
    void getAllDto() throws JsonProcessingException {
        //lazy loading
        PageAccountDto allDto = accountService.getAllDto("", "", "", "", null, null);
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(allDto));
    }

    @Test
    void emptyStringTest() {
        boolean notEmpty = StringUtils.isNotBlank(null);
        System.out.println(notEmpty);
    }

    @Test
    void getById() {
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