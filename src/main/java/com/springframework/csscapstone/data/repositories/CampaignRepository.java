package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CampaignRepository extends JpaRepository<Campaign, UUID>, JpaSpecificationExecutor<Campaign> {
}