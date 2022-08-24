package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
public interface CustomerRepository extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {
    Optional<Customer> getCustomerByPhone(String phone);

    @Query("SELECT a FROM Customer a WHERE a.phone = :phone")
    Optional<Customer> findByPhone(@Param("phone") String phone);

    List<Customer> findAllByAccountCreator_Id(UUID collaboratorId);

}