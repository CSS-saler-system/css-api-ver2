package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @Transactional(readOnly = true)
    Optional<Customer> getCustomerByPhone(String phone);


}