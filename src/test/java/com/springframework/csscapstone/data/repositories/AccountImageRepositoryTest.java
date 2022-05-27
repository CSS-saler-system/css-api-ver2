package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.AccountImage;
import com.springframework.csscapstone.data.status.AccountImageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@ActiveProfiles(value = "test")
@AutoConfigureTestDatabase(replace = NONE)
class AccountImageRepositoryTest {
    @Autowired
    AccountImageRepository accountImageRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findByAccountAndType() {
        Optional<AccountImage> byAccountAndType = accountImageRepository
                .findByAccountAndType(UUID.fromString("3e7693f2-06db-4e0a-98a1-99c978ed7b68"),
                        AccountImageType.AVATAR);
        byAccountAndType.ifPresent(System.out::println);
    }
}