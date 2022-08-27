package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, UUID>, JpaSpecificationExecutor<Account> {
    String QUERY_ACCOUNT = "tuple_account";
    String QUERY_COUNT_REQ = "tuple_count_req";

    Optional<Account> findAccountByEmail(String email);
    Optional<Account> findAccountByPhone(String phone);
    @Query(value = "SELECT a FROM Account a WHERE a.role.name = :role")
    List<Account> findSingleAccountByRole(@Param("role") String role);

//    Optional<Account> findAccountByEmail(String email);

    Optional<Account> findAccountsByPhone(String phone);

    Optional<List<Account>> findAccountByEmailOrPhone(String email, String phone);

    @Query(value = "SELECT a.id AS " + QUERY_ACCOUNT + ", COUNT(req) AS " + QUERY_COUNT_REQ + " " +
            "FROM Account a " +
            "JOIN a.products p " +
            "JOIN p.requestSellingProducts req " +
            "WHERE a.role.name = 'Enterprise' " +
            "AND req.requestStatus = 'REGISTERED' " +
            "GROUP BY a.id",
    countQuery = "SELECT count(a) FROM Account a WHERE a.role.name = 'Enterprise'")
    Page<Tuple> findAccountEnterpriseForCollaborator(Pageable pageable);

    @Query("SELECT a FROM Account a WHERE a.role.name =: role")
    List<Account> findAllFetchJoinRole(@Param("role") String roleName);

    @Query("SELECT a FROM Account a WHERE a.role = 'ROLE_2' AND a.isActive = true")
    Page<Account> findAllEnterprise(Pageable pageable);

    @Query("SELECT COUNT(a) FROM Account a " +
            "WHERE a.role.name = 'Collaborator' " +
            "AND a.isActive = true")
    long countCollaborator();

    @Query("SELECT COUNT(a) FROM Account a " +
            "WHERE a.role.name = 'Enterprise' " +
            "AND a.isActive = true ")
    long countEnterprise();

//    @Query("SELECT a FROM Account a WHERE a.role.name = 'Enterprise'")
//    Page<Account> getAllEnterprise(Pageable pageable);

}