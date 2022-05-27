package com.springframework.csscapstone.data.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.status.AccountImageType;
import com.springframework.csscapstone.utils.mapper_utils.MapperDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Collectors;

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
        accountRepository
//                .findAllFetchJoinRole()
                .findAll()
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

        this.accountRepository.findAccountByRole("Collaborator", null)
                .stream().peek(
                        x -> {
                            x.getImages()
                                    .stream()
                                    .filter(image -> image.getType().equals(AccountImageType.AVATAR))
                                    .collect(Collectors.toList());
                            x.getImages().stream().filter(image -> image.getType().equals(AccountImageType.LICENSE));
                            x.getImages().stream().filter(image -> image.getType().equals(AccountImageType.ID_CARD));
                        }
                )
                .map(MapperDTO.INSTANCE::toAccountResponseDto)
                .forEach(x -> {
                    try {
                        String json = new ObjectMapper()
                                .writerWithDefaultPrettyPrinter()
                                .writeValueAsString(x);
                        System.out.println(json);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}