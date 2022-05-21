package com.springframework.csscapstone.data.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.EnterpriseDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.EnterprisePageImpl;
import com.springframework.csscapstone.utils.mapper_utils.MapperDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ComponentScan("com.springframework.csscapstone.data")
@ActiveProfiles(value = "test")
class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAccountByUsername() {
        Optional<Account> sumo6842 = this.accountRepository
                .findAccountByEmail("sumo6842@gmail.com");
        sumo6842.get().getAvatar().forEach(System.out::println);
    }

    @Test
    void findAccountByEmail() {
    }

    @Test
    void findAccountsByPhone() {
    }

    @Test
    void findAccountByUsernameOrEmailOrPhone() {
    }

    @Test
    void findAccountByRole() {
        this.accountRepository
                .findAccountByRole("Enterprise")
                .forEach(System.out::println);
    }

    @Test
    void getAvatarAccount() {
        Account account = this.accountRepository
                .findAccountByEmail("sumo6842@gmail.com")
                .get();
        boolean matches = new BCryptPasswordEncoder().matches("Linhduc13", account.getPassword());

        System.out.println(account);
        System.out.println(matches);
    }

    @Test
    void getPageAccountTest() throws JsonProcessingException {
        Page<Account> page = this.accountRepository
                .getAllEnterprise(
//                        Pageable.ofSize(5).withPage(2)
                        PageRequest.of(1, 5)
                );
        page.forEach(System.out::println);
        System.out.println("Total Element: " + page.getTotalElements());
        System.out.println("Total page: " + page.getTotalPages());
        System.out.println("Pageable: " + page.getPageable());


        List<EnterpriseDto> collect = page.getContent().stream().map(MapperDTO.INSTANCE::toEnterpriseDto).collect(toList());
        EnterprisePageImpl pageImpl = new EnterprisePageImpl(collect,
                page.getNumber() + 1,
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast());
//
        System.out.println(new ObjectMapper().writeValueAsString(pageImpl));
    }

}