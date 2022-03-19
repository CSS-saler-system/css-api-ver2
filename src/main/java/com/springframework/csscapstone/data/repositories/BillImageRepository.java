package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.controller.domain.BillImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillImageRepository extends JpaRepository<BillImage, Long> {
}