package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @Transactional(readOnly = true)
    Optional<Customer> getCustomerByPhone(String phone);


    @Transactional
    @Query("SELECT a FROM Customer a WHERE a.phone = :phone")
    Optional<Customer> findByPhone(@Param("phone") String phone);

}