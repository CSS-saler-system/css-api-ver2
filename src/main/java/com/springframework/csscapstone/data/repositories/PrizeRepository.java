package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Prize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PrizeRepository extends JpaRepository<Prize, UUID>, JpaSpecificationExecutor<Prize> {
}