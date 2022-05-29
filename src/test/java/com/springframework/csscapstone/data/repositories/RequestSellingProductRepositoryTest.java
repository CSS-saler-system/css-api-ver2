package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.status.RequestStatus;
import com.springframework.csscapstone.utils.mapper_utils.MapperDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles(value =  "test")
class RequestSellingProductRepositoryTest {

    @Autowired
    RequestSellingProductRepository requestSellingProductRepository;


    @Test
    void findAllByRequestStatus() {
        requestSellingProductRepository.findAllByRequestStatus(RequestStatus.PENDING, PageRequest.of(0, 100))
                .map(MapperDTO.INSTANCE::toRequestSellingProductDto)
                .forEach(System.out::println);

    }
}