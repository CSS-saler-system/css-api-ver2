package com.springframework.csscapstone.data.domain;

import com.springframework.csscapstone.data.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Replace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
//@Acti("text")
@ActiveProfiles(value = "test")
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void getAllProductRepositoryTest() {
        this.productRepository.findAll().forEach(System.out::println);
    }
}
