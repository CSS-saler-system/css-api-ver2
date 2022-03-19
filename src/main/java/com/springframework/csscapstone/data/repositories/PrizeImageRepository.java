package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.PrizeImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PrizeImageRepository extends JpaRepository<PrizeImage, UUID> {
}