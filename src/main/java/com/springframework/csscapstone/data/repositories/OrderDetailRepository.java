package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.OrderDetail;
import com.springframework.csscapstone.data.domain.Product;
import com.springframework.csscapstone.data.status.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Transactional(readOnly = true)
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
    @Query(
            value = "SELECT od.product, sum(od.quantity) " +
                    "FROM OrderDetail od " +
                    "JOIN od.order o " +
                    "WHERE o.createDate >= :startDate  " +
                    "AND o.createDate <= :endDate " +
                    "AND o.status = :status " +
                    "group by od.product",
            countQuery = "SELECT count(od.product) " +
                    "FROM OrderDetail od " +
                    "JOIN od.order o " +
                    "WHERE o.createDate >= :startDate  " +
                    "AND o.createDate <= :endDate " +
                    "AND o.status = :status " +
                    "group by od.product")
    Page<Map<Product, Integer>> findAllSumInOrderDetailGroupingByProduct(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("status") OrderStatus status,
            Pageable pageable);

}