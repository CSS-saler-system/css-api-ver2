package com.springframework.csscapstone.css_data.repositories;

import com.springframework.csscapstone.css_data.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CampaignRepository extends JpaRepository<Campaign, UUID> {
}