package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.OrderDetail;
import com.springframework.csscapstone.data.status.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.UUID;

@Transactional(readOnly = true)
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {

    String PRODUCT = "tuple_product";
    String COUNT = "tuple_count";

    /**
     * @param enterpriseId
     * @param startDate
     * @param endDate
     * @param status
     * @param pageable
     * @return
     */
    @Query(
            value = "SELECT DISTINCT od.product.id AS " + PRODUCT + ", " +
                    "sum(od.quantity) AS " + COUNT + " " +
                    "FROM OrderDetail od " +
                    "JOIN od.order o " +
                    "JOIN od.product p " +
                    "WHERE  o.createDate >= :startDate  " +
                    "AND o.createDate <= :endDate " +
                    "AND o.status = :status " +
                    "AND od.product.account.id = :enterpriseId " +
                    "AND not p.productStatus = 'DISABLED' " +
                    "GROUP BY od.product.id",
            countQuery = "SELECT count(distinct od.product.id) " +
                    "FROM OrderDetail od " +
                    "JOIN od.order o " +
                    "JOIN od.product p " +
                    "WHERE o.createDate >= :startDate  " +
                    "AND o.createDate <= :endDate " +
                    "AND o.status = :status " +
                    "AND not p.productStatus = 'DISABLED' " +
                    "AND od.product.account.id = :enterpriseId " +
                    "GROUP BY od.product.id")
    Page<Tuple> findAllSumInOrderDetailGroupingByProduct(
            @Param("enterpriseId") UUID enterpriseId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("status") OrderStatus status,
            Pageable pageable);


}