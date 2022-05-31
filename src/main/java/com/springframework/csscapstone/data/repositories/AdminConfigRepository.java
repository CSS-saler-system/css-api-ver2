package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.AdminConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AdminConfigRepository extends JpaRepository<AdminConfig, Long> {
}