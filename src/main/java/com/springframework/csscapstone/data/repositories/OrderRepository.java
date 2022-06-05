package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Order;
import com.springframework.csscapstone.payload.queries.QueriesAccountProductSummingDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Transactional(readOnly = true)
public interface OrderRepository extends JpaRepository<Order, UUID> {

    //(collab, product) -> all order -> order detail -> sum quantity
    @Query(
            value = "SELECT sum(od.quantity) " +
                    "FROM Order o " +
                    "JOIN o.account " +
                    "JOIN o.orderDetails od " +
                    "WHERE od.product.id = :idProduct " +
                    "AND o.account.id = :accountid "
    )
    QueriesAccountProductSummingDto getAllSummingProduct();

}