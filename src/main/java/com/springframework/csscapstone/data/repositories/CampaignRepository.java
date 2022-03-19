package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.controller.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CampaignRepository extends JpaRepository<Campaign, UUID> {
}