package com.springframework.csscapstone.data.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.data.status.OrderStatus;
import com.springframework.csscapstone.payload.queries.QueriesProductDto;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.MapperDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ActiveProfiles(value = "test")
//@ComponentScan(basePackages = {"com.springframework.csscapstone.utils.mapper_utils"})
class OrderDetailRepositoryTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    OrderDetailRepository repository;
    @Autowired
    ProductRepository productRepository;

    @Test
    void findAllSumInOrderDetailGroupingByProduct() {
        Page<QueriesProductDto> result = repository.findAllSumInOrderDetailGroupingByProduct(
                LocalDateTime.of(1999, 1, 3, 3, 3, 59),
                LocalDateTime.now(),
                OrderStatus.FINISH,
                PageRequest.of(0, 100)
        );
        result.getContent()
                .stream().map(x -> {
                    try {
                        return new ObjectMapper()
                                .writerWithDefaultPrettyPrinter()
                                .writeValueAsString(x);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .forEach(System.out::println);
    }

    @Test
    void getProductTest() {
        this.productRepository
                .findById(UUID.fromString("54d9dc82-36d0-ec11-9d64-0242ac120002"))
                .map(MapperDTO.INSTANCE::toProductResponseDto)
                .ifPresent(x -> {
                    try {
                        String productDto = new ObjectMapper().writerWithDefaultPrettyPrinter()
                                .writeValueAsString(x);
                        System.out.println("This is result");
                        System.out.println(productDto);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}