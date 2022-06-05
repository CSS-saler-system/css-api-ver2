package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.RequestSellingProduct;
import com.springframework.csscapstone.data.status.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Transactional(readOnly = true)
public interface RequestSellingProductRepository extends JpaRepository<RequestSellingProduct, UUID> {

    //todo Get request selling product by enterprise id
    @Query(value =
            "SELECT r FROM RequestSellingProduct r " +
                    "JOIN r.accounts a " +
                    "WHERE r.requestStatus = :requestStatus AND a.id = :enterpriseId",
            countQuery = "SELECT count(r) FROM RequestSellingProduct r " +
                    "JOIN r.accounts a " +
                    "WHERE r.requestStatus = :requestStatus AND a.id = :enterpriseId")
    Page<RequestSellingProduct> findAllByRequestStatus(
            @Param("enterpriseId") UUID enterpriseId,
            @Param("requestStatus") RequestStatus requestStatus,
            Pageable pageable);

    @Query(
            value =
                    "SELECT r FROM RequestSellingProduct r " +
                            "JOIN r.accounts a " +
                            "WHERE a.id = :enterpriseId " +
                            "AND r.requestStatus = :status ",
            countQuery =
                    "SELECT COUNT(r) " +
                            "FROM RequestSellingProduct r JOIN r.accounts a " +
                            "WHERE a.id = :enterpriseId " +
                            "AND r.requestStatus = :status")
    Page<RequestSellingProduct> findAllRequestSellingProduct(
            @Param("enterpriseId") UUID enterpriseId,
            @Param("status") RequestStatus status, Pageable pageable);


//    @Query(value =
//            "SELECT r.accounts FROM RequestSellingProduct r " +
//            "JOIN r.accounts a " +
//            "WHERE r.requestStatus = :status AND a.id = :enterpriseId")//Registered
//    List<Account> findAccountInRequestSelling(@Param("enterpriseId") UUID enterpriseId, @Param("status") RequestStatus status);

    /**
     * todo get all account from request selling product follow product and enterprise
     * @param enterpriseId
     * @param status
     * @return
     */
    @Query(value =
            "SELECT a.id, sum(od.quantity) FROM RequestSellingProduct r " +
                    "JOIN r.product p " +
                    "JOIN r.accounts a " +
                    "JOIN a.orders o " +
                    "JOIN o.orderDetails od " +
                    "WHERE p.account.id = :enterpriseId " +
                    "AND r.requestStatus = :status " +
                    "GROUP BY a.id")
    List<Object[]> findAccountInRequestSelling(
            @Param("enterpriseId") UUID enterpriseId,
            @Param("status") RequestStatus status);

}
