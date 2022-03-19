package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.RequestSellingProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RequestSellingProductRepository extends JpaRepository<RequestSellingProduct, UUID> {
}