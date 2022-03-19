package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.controller.domain.Prize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PrizeRepository extends JpaRepository<Prize, UUID> {
}