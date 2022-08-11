package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Transactions;
import com.springframework.csscapstone.data.status.TransactionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Transactional(readOnly = true)
public interface TransactionsRepository
        extends JpaRepository<Transactions, UUID>, JpaSpecificationExecutor<Transactions> {

    @Query(value =
            "SELECT t " +
                    "FROM Transactions t " +
                    "WHERE t.transactionCreator.id = :id " +
                    "AND t.LastModifiedDate >= :startDate " +
                    "AND t.LastModifiedDate <= :endDate " +
                    "AND NOT t.transactionStatus = :status",
            countQuery =
                    "SELECT count(t) " +
                            "FROM Transactions t " +
                            "WHERE t.transactionCreator.id = :id " +
                            "AND t.LastModifiedDate >= :startDate " +
                            "AND t.LastModifiedDate <= :endDate " +
                            "AND NOT t.transactionStatus = :status")
    Page<Transactions> findAllByDate(
            @Param("id") UUID id,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("status") TransactionStatus status,
            Pageable pageable);

    @Query("SELECT t FROM Transactions t where not t.transactionStatus = 'DISABLED'")
    Page<Transactions> findAllByPendingStatus(Pageable pageable);

}