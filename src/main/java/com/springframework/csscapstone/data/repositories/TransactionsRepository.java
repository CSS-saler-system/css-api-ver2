package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional(readOnly = true)
public interface TransactionsRepository
        extends JpaRepository<Transactions, UUID>, JpaSpecificationExecutor<Transactions> {

//    @Query(value = "SELECT t FROM Transactions t " +
//            "WHERE NOT EXISTS (SELECT T1 FROM Transactions T1 WHERE T1.transactionStatus = 'DISABLED')")
//    Page<Transactions> findAll(Pageable pageable);

}