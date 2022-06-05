package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Transactional(readOnly = true)
public interface OrderRepository extends JpaRepository<Order, UUID> {


}