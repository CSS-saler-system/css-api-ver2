package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends PagingAndSortingRepository<Account, UUID>, JpaSpecificationExecutor<Account> {
//    Optional<Account> findAccountByUsername(String username);
    @Transactional(readOnly = true)
    Optional<Account> findAccountByEmail(String email);

    @Transactional(readOnly = true)
    Optional<Account> findAccountsByPhone(String phone);

    @Transactional(readOnly = true)
    Optional<List<Account>> findAccountByEmailOrPhone(String email, String phone);

    @Transactional(readOnly = true)
    @Query("SELECT a FROM Account a LEFT JOIN FETCH a.role LEFT JOIN FETCH a.avatar WHERE a.role.name = :role")
    List<Account> findAccountByRole(@Param("role") String role);

    @Query("SELECT a FROM Account a")
    Page<Account> getAllEnterprise(Pageable pageable);

}