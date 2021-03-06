package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.CampaignImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CampaignImageRepository extends JpaRepository<CampaignImage, UUID> {
}