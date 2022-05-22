package com.springframework.csscapstone.data.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ComponentScan("com.springframework.csscapstone.data")
@ActiveProfiles(value = "test")
class RoleRepositoryTest {

    @Autowired
    RoleRepository roleRepository;

    @Test
    void findByNameRole() {
        roleRepository
                .findAllByName("Enterprise")
                .forEach(System.out::println);
    }
}