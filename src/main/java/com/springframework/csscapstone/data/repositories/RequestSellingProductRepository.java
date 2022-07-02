package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.RequestSellingProduct;
import com.springframework.csscapstone.data.status.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional(readOnly = true)
public interface RequestSellingProductRepository extends JpaRepository<RequestSellingProduct, UUID>,
        JpaSpecificationExecutor<RequestSellingProduct> {

    @Query(value = "" +
            "SELECT r FROM RequestSellingProduct r " +
            "WHERE r.account.id = :idCollaborator " +
            "AND r.requestStatus = :status")
    Page<RequestSellingProduct> findRequestSellingProductByCollaborator(
            @Param("idCollaborator") UUID idCollaborator,
            @Param("status") RequestStatus status,
            Pageable pageable);

    // =============================== Enterprise ===========================================
    //todo Get request selling product by enterprise id
    @Query(value =
            "SELECT r FROM RequestSellingProduct r " +
                    "LEFT JOIN r.product p " +
                    "WHERE r.requestStatus = :requestStatus AND p.account.id = :enterpriseId",

            countQuery = "SELECT count(r) FROM RequestSellingProduct r " +
                    "JOIN r.product p " +
                    "WHERE r.requestStatus = :requestStatus AND p.account.id = :enterpriseId")
    Page<RequestSellingProduct> findAllByRequestStatusByEnterprise(
            @Param("enterpriseId") UUID enterpriseId,
            @Param("requestStatus") RequestStatus requestStatus,
            Pageable pageable);

    //todo select product on request
    //todo enterprise role
    @Query(
            value =
                    "SELECT distinct r.account FROM RequestSellingProduct r " +
                            "JOIN r.product p " +
                            "JOIN p.account a " +
                            "WHERE a.id = :enterpriseId " +
                            "AND r.requestStatus = :status ",
            countQuery =
                    "SELECT COUNT(distinct r.account) " +
                            "FROM RequestSellingProduct r " +
                            "JOIN r.product p " +
                            "JOIN p.account a " +
                            "WHERE a.id = :enterpriseId " +
                            "AND r.requestStatus = :status")
    Page<Account> findAllCollaboratorByRequestSellingProduct(
            @Param("enterpriseId") UUID enterpriseId,
            @Param("status") RequestStatus status, Pageable pageable);


}
