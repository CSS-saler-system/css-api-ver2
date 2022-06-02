package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.config.constant.ApiEndPoint;
import com.springframework.csscapstone.data.status.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles(value = "test")
class OrderDetailRepositoryTest {

    @Autowired
    OrderDetailRepository repository;

    @Test
    void findAllSumInOrderDetailGroupingByProduct() {
//        repository.findAllSumInOrderDetailGroupingByProduct(
//                LocalDateTime.of(1999, 1, 3, 3, 3, 123),
//                LocalDate.now(), OrderStatus.FINISH,
//                PageRequest.of(0, 100)
//                );
    }
}