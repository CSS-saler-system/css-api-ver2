package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.AdminConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminConfigRepository extends JpaRepository<AdminConfig, Long> {
}