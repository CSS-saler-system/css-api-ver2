package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Order;
import com.springframework.csscapstone.data.status.OrderStatus;
import org.springframework.data.domain.Page;
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

    @Query("SELECT o FROM Order o " +
            "WHERE o.account = :idCollaborator " +
            "AND o.status = :status")
    Page<Order> pageOrderCollaboratorCreate(Account idCollaborator, OrderStatus status , Pageable pageable);

    @Query("SELECT o FROM Order o " +
            "WHERE o.account.id = :idCollaborator " +
            "AND o.status = :status")
    Page<Order> pageOrderCollaboratorCreateForTest(UUID idCollaborator, OrderStatus status , Pageable pageable);

    /**
     * todo The method get list collaborator and quantity sold product sort desc
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
                    "ORDER BY sum(od.quantity) DESC")
    Page<Tuple> sortedPageCollaboratorByQuantitySelling(@Param("enterpriseId") UUID enterpriseId, Pageable pageable);

    //todo for mapping: get account then quantity and sort asc
    @Query(
            "SELECT a.id as " + COLL_ID + " , " +
                    "sum(od.quantity) as " + TOTAL_QUANTITY + " " +
                    "FROM Order o " +
                    "JOIN o.orderDetails od " +
                    "JOIN o.account a " +
                    "WHERE od.product.id = :idProduct " +
                    "GROUP BY a.id " +
                    "ORDER BY sum(od.quantity) DESC")
    List<Tuple> getCollaboratorAndTotalQuantitySold(@Param("idProduct") UUID idProduct);


}