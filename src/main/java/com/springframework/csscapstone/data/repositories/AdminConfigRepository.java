package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.controller.domain.AdminConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminConfigRepository extends JpaRepository<AdminConfig, Long> {
}