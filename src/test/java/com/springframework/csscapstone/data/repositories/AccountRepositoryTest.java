package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.AccountImage;
import com.springframework.csscapstone.payload.basic.RoleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles(value = "test")
class AccountRepositoryTest {

    @Autowired
    AccountImageRepository accountImageRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAllTest() {
        accountRepository.findAll().stream().map(Account::getId).forEach(System.out::println);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void insertImageToAccountTest() {
        AccountImage accountImage = new AccountImage();
        this.accountImageRepository.save(accountImage);
//        String id = "aa55aa99-d753-4048-aabb-924dfd1051a1";
        String id = "a52bc323-4a03-e947-a9f3-9d0daf224726";
        Optional<Account> byId = this.accountRepository.findById(UUID.fromString(id));
        System.out.println("This is account" + byId.get());
        byId
                .ifPresent(acc -> acc.addImage(new AccountImage[]{accountImage}));
        this.accountRepository.save(byId.get());
    }

    @Test
    void findAccountByEmail() {
        Optional<Account> accountByEmail = this.accountRepository.findAccountByEmail("ben@gmail.com");
        accountByEmail.ifPresent(System.out::println);
    }

    @Test
    void findAccountsByPhone() {
        Optional<Account> accountByEmail = this.accountRepository.findAccountsByPhone("0345184306");
        accountByEmail.ifPresent(System.out::println);
    }

    @Test
    void findAccountByEmailOrPhone() {
        this.accountRepository.findAccountByEmailOrPhone("sumo6842@gmail.com", "0345184306")
                .ifPresent(System.out::println);
    }

    @Test
    void findAccountByRoleWithEntityManagerTest() {
//        String id = "aa55aa99-d753-4048-aabb-924dfd1051a1";
        String id = "a52bc323-4a03-e947-a9f3-9d0daf224726";
        List resultList = entityManager.createQuery(
                "SELECT a " +
                        "FROM Account a " +
                        "LEFT JOIN FETCH a.images " +
                        "WHERE a.role.name =: role")
                .setParameter("role", "Collaborator")
                .getResultList();
        resultList.forEach(System.out::println);
    }

    @Test
    void findAccountWithRepositoryTest() {
        long collaborator = this.accountRepository
                .findAccountByRole("Collaborator").stream()
                .peek(System.out::println)
                .count();
        System.out.println(collaborator);
    }
    @Test
    void findAccountWithRepositoryPagingTest() {
        Page<Account> enterprise = this.accountRepository
                .findAccountByRole("Collaborator",null);
        long count = enterprise.getContent()
                .stream().peek(System.out::println)
                .count();
        System.out.println(count);

        assertEquals(count, 20);

    }

}