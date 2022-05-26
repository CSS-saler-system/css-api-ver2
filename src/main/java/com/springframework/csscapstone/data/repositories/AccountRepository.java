package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, UUID>, JpaSpecificationExecutor<Account> {

    Optional<Account> findAccountByEmail(String email);

    Optional<Account> findAccountsByPhone(String phone);

    @Query("SELECT a FROM Account a WHERE a.phone =:phone or a.email=:email")
    Optional<Account> findAccountByEmailOrPhone(String email, String phone);

    @Query(
            value = "SELECT a FROM Account a " +
                    "LEFT JOIN FETCH a.images i " +
                    "WHERE a.role.name = :role")
    List<Account> findAccountByRole(@Param("role") String role);

    @Query(
            value = "SELECT a FROM Account a " +
                    "LEFT JOIN FETCH a.images i " +
                    "WHERE a.role.name = :role",
            countQuery = "SELECT COUNT(a) " +
                    "FROM Account a " +
                    "WHERE a.role.name =:role")
    Page<Account> findAccountByRole(@Param("role") String role, Pageable pageable);

}