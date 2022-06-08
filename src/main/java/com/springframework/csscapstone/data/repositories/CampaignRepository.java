package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional(readOnly = true)
public interface CampaignRepository extends JpaRepository<Campaign, UUID>,
        JpaSpecificationExecutor<Campaign> {
}