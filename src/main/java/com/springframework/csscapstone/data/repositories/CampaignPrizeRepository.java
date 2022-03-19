package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.CampaignPrize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CampaignPrizeRepository extends JpaRepository<CampaignPrize, UUID> {
}