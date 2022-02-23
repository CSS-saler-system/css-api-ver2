package com.springframework.csscapstone.css_data.repositories;

import com.springframework.csscapstone.css_data.domain.CampaignPrize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CampaignPrizeRepository extends JpaRepository<CampaignPrize, UUID> {
}