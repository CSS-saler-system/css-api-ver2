package com.springframework.csscapstone.css_data.repositories;

import com.springframework.csscapstone.css_data.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
}