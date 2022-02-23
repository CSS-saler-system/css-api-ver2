package com.springframework.csscapstone.css_data.repositories;

import com.springframework.csscapstone.css_data.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends PagingAndSortingRepository<Account, UUID> {
    Optional<Account> findAccountByUsername(String username);

    Optional<Account> findAccountByEmail(String email);

    Optional<Account> findAccountsByPhone(String phone);

    Optional<List<Account>> findAccountByUsernameOrEmailOrPhone(String username, String email, String phone);

//    Page<Account> findAccountByPhoneOrEmail(String phone, String email, Pageable pageable);

//    Page<Account> findAllByAccountStatus(AccountStatus status, Pageable pageable);



//
//    @Query("From Account a " +
//            "WHERE a.phone = :phone " +
//            "and a.email = :email " +
//            "and CONCAT(a.name, ' ', a.address, ' ', a.description) LIKE :other " +
//            "and a.accountStatus = :status ")
//    Page<Account> findAllByCustomizeCondition(
//            @Param("phone") String phone,
//            @Param("email") String email,
//            @Param("other") String other,
//            @Param("status") AccountStatus status, Pageable pageable);

}