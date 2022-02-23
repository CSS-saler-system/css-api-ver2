package com.springframework.csscapstone.css_data.repositories;

import com.springframework.csscapstone.css_data.domain.Prize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PrizeRepository extends JpaRepository<Prize, UUID> {
}