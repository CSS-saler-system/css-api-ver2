package com.springframework.csscapstone;

import com.springframework.csscapstone.data.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles(value = "test")
public class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @Test
    void getAllAccountTest() {
        accountRepository.findAll()
                .forEach(System.out::println);
    }
}
