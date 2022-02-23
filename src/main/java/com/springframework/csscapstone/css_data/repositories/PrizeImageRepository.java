package com.springframework.csscapstone.css_data.repositories;

import com.springframework.csscapstone.css_data.domain.PrizeImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PrizeImageRepository extends JpaRepository<PrizeImage, UUID> {
}