package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.List;
import java.util.UUID;
@Transactional(readOnly = true)
public interface OrderRepository extends JpaRepository<Order, UUID> {
    /**
     * todo The method get list collaborator and quantity sold product sort asc
     * @param enterpriseId
     * @return
     */
    @Query(
            "SELECT col.id as col_id, sum(od.quantity) as total_quantity " +
                    "FROM Order o " +
                    "JOIN o.account col " +
                    "JOIN o.orderDetails od " +
                    "JOIN od.product p " +
                    "WHERE od.quantity > 0 " +
                    "AND p.account.id = :enterpriseId " +
                    "AND o.status = 'FINISH'" +
                    "GROUP BY col.id " +
                    "ORDER BY sum(od.quantity) ASC")
    List<Tuple> sortCollaboratorSold(@Param("enterpriseId") UUID enterpriseId);

}