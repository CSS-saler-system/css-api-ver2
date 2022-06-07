package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.List;
import java.util.UUID;
@Transactional(readOnly = true)
public interface OrderRepository extends JpaRepository<Order, UUID> {
    String COLL_ID = "col_id";
    String TOTAL_QUANTITY = "total_quantity";

    /**
     * todo The method get list collaborator and quantity sold product sort asc
     * @param enterpriseId
     * @return
     */
    @Query(
            "SELECT col.id as " + COLL_ID + ", " +
                    "sum(od.quantity) as " + TOTAL_QUANTITY + " " +
                    "FROM Order o " +
                    "JOIN o.account col " +
                    "JOIN o.orderDetails od " +
                    "JOIN od.product p " +
                    "WHERE od.quantity > 0 " +
                    "AND p.account.id = :enterpriseId " +
                    "AND o.status = 'FINISH'" +
                    "GROUP BY col.id " +
                    "ORDER BY sum(od.quantity) ASC")
    Page<Tuple> sortCollaboratorSold(@Param("enterpriseId") UUID enterpriseId, Pageable pageable);

}