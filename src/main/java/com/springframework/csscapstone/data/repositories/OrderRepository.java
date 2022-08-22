package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Order;
import com.springframework.csscapstone.data.status.OrderStatus;
import com.springframework.csscapstone.payload.response_dto.enterprise.OrderChartResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
public interface OrderRepository extends JpaRepository<Order, UUID>, JpaSpecificationExecutor<Order> {
    String COLLABORATOR_IDS = "col_id";
    String TOTAL_QUANTITY = "total_quantity";
    String CATEGORY = "tuple_category";
    String QUANTITY_SOLD = "tuple_sold";
    String ORDER_DATE = "tuple_date";
    String ORDER_REVENUE = "tuple_revenue";
    String ORDER_TOTAL = "tuple_order_total";
    String ORDER_BY_MONTH = "tuple_order_month";

    @Query("SELECT o FROM Order o " +
            "WHERE o.account = :idCollaborator " +
            "AND o.status = :status")
    Page<Order> pageOrderCollaboratorCreate(Account idCollaborator, OrderStatus status, Pageable pageable);

    @Query("SELECT o FROM Order o " +
            "WHERE o.account.id = :idCollaborator " +
            "AND o.status = :status")
    Page<Order> pageOrderCollaboratorCreateForTest(UUID idCollaborator, OrderStatus status, Pageable pageable);

    /**
     * todo The method get list collaborator and quantity sold product sort desc
     *
     * @param enterpriseId
     * @return
     */
    @Query(
            "SELECT col.id as " + COLLABORATOR_IDS + ", " +
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
            "SELECT _collaborator.id as " + COLLABORATOR_IDS + " , " +
                    "sum(od.quantity) as " + TOTAL_QUANTITY + " " +
                    "FROM Order o " +
                    "JOIN o.orderDetails od " +
                    "JOIN o.account _collaborator " +
                    "WHERE od.product.id = :idProduct " +
                    "GROUP BY _collaborator.id " +
                    "ORDER BY sum(od.quantity) DESC")
    List<Tuple> getCollaboratorAndTotalQuantitySold(@Param("idProduct") UUID idProduct);

    /**
     * todo list query separate| need more test
     *
     * @param collaboratorId
     * @return
     */
    @Query(value = "SELECT c AS " + CATEGORY + ", SUM(od.quantity) AS " + QUANTITY_SOLD + " " +
            "FROM Order o " +
            "JOIN o.orderDetails od " +
            "JOIN od.product p " +
            "JOIN p.category c " +
            "WHERE o.account.id = :collaboratorId " +
            "GROUP BY c")
    List<Tuple> getCollaboratorWithPerformance(UUID collaboratorId);

    /**
     * todo list query separate
     * WORK
     *
     * @param collaboratorId
     * @return
     */
    @Query(value = "SELECT c.id AS " + CATEGORY + ", SUM(od.quantity) AS " + QUANTITY_SOLD + " " +
            "FROM Order o " +
            "JOIN o.orderDetails od " +
            "JOIN od.product p " +
            "JOIN p.category c " +
            "WHERE o.account.id = :collaboratorId " +
            "GROUP BY c.id")
    List<Tuple> getCollaboratorWithPerformanceById(UUID collaboratorId);


    @Query(value = "SELECT distinct o " +
            "FROM Order  o " +
            "JOIN o.orderDetails od " +
            "JOIN od.product p " +
            "JOIN p.account enterprise " +
            "WHERE enterprise.id = :enterpriseId " +
            "AND NOT o.status = :status",
            countQuery = "SELECT count( distinct o) " +
                    "FROM Order  o " +
                    "JOIN o.orderDetails od " +
                    "JOIN od.product p " +
                    "JOIN p.account enterprise " +
                    "WHERE enterprise.id = :enterpriseId " +
                    "AND NOT o.status = :status")
    Page<Order> getOrderByEnterprise(@Param("enterpriseId") UUID enterpriseId, @Param("status") OrderStatus status, Pageable pageable);


    /**
     * SELECT MONTH(o.create_date) AS ORDER_MONTH,
     * sum(o.total_price) AS ORDER_REVENUE
     * FROM orders o GROUP BY MONTH(o.create_date)
     *
     * @param id
     * @return
     */
    @Query("SELECT MONTH(o.createDate) AS " + ORDER_DATE + ", " +
            "sum(o.totalPrice) AS " + ORDER_REVENUE + " " +
            "FROM Order o " +
            "JOIN o.orderDetails od " +
            "JOIN od.product p " +
            "JOIN p.account a " +
            "WHERE o.status = 'FINISH' " +
            "AND a.id = :id " +
            "GROUP BY MONTH(o.createDate)")
    List<Tuple> getRevenueByEnterprise(UUID id);

    /**
     * String ORDER_TOTAL = "tuple_order_total";
     * String ORDER_BY_MONTH = "tuple_order_month";
     *
     * @return
     */
    @Query("SELECT COUNT(distinct o) AS " + ORDER_TOTAL + " , " +
            "month(o.createDate) AS " + ORDER_BY_MONTH + " " +
            "FROM Order o " +
            "JOIN o.orderDetails od " +
            "JOIN od.product p " +
            "WHERE p.account.id =:enterpriseId " +
            "GROUP BY month(o.createDate)")
    List<Tuple> getOrderByMonth(@Param("enterpriseId") UUID enterpriseId);



}