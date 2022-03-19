package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.controller.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
}