package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
public interface CampaignRepository extends JpaRepository<Campaign, UUID>,
        JpaSpecificationExecutor<Campaign> {

    @Query("SELECT _campaign FROM Campaign _campaign " +
            "LEFT JOIN FETCH _campaign.products " +
            "LEFT JOIN FETCH _campaign.prizes " +
            "WHERE NOT EXISTS (SELECT c FROM Campaign c WHERE c.campaignStatus = 'FINISHED')" +
            "AND _campaign.id = :id")
    Optional<Campaign> loadFetchOnProducts(@Param("id") UUID id);

}