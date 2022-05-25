package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles(value = "test")
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;
    @BeforeEach
    void setUp() {
    }

    @Test
    void findAllTest() {
        accountRepository.findAll().stream()
                .map(Account::getId)
                .forEach(System.out::println);
    }

    @Test
    void findAccountByEmail() {
    }

    @Test
    void findAccountsByPhone() {
    }

    @Test
    void findAccountByEmailOrPhone() {
    }

    @Test
    void findAccountByRole() {
    }
}