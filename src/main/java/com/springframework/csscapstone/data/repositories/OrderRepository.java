package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}