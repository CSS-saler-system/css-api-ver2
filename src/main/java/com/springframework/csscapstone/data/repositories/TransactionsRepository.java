package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Transactional(readOnly = true)
public interface TransactionsRepository
        extends JpaRepository<Transactions, UUID>, JpaSpecificationExecutor<Transactions> {

//    @Query(value = "SELECT t FROM Transactions t " +
//            "WHERE NOT EXISTS (SELECT T1 FROM Transactions T1 WHERE T1.transactionStatus = 'DISABLED')")
//    Page<Transactions> findAll(Pageable pageable);

    @Query(value =
            "SELECT t " +
                    "FROM Transactions t " +
                    "WHERE t " +
                    "NOT IN(SELECT tmp FROM Transactions tmp WHERE tmp.transactionStatus = 'DISABLE') " +
                    "AND t.transactionCreator.id = :id " +
                    "AND t.LastModifiedDate >= :startDate " +
                    "AND t.LastModifiedDate <= :endDate ",
            countQuery =
                    "SELECT count(t) " +
                            "FROM Transactions t " +
                            "WHERE t " +
                            "NOT IN(SELECT tmp FROM Transactions tmp WHERE tmp.transactionStatus = 'DISABLE') " +
                            "AND t.transactionCreator.id = :id " +
                            "AND t.LastModifiedDate >= :startDate " +
                            "AND t.LastModifiedDate <= :endDate ")
    Page<Transactions> findAllByDate(
            @Param("id") UUID id,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    @Query("SELECT t FROM Transactions t where t.transactionStatus = 'PENDING'")
    Page<Transactions> findAllByPendingStatus(Pageable pageable);

}