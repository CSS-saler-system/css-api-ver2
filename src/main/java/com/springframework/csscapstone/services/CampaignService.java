package com.springframework.csscapstone.services;

import com.springframework.csscapstone.data.status.CampaignStatus;
import com.springframework.csscapstone.payload.basic.CampaignDto;
import com.springframework.csscapstone.payload.custom.creator_model.CampaignCreatorDto;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.campaign_exception.CampaignInvalidException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CampaignService {

    List<CampaignDto> findCampaign(
            String name, LocalDateTime createdDate,
            LocalDateTime lastModifiedDate,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String description,
            Long kpi,
            CampaignStatus status);

    CampaignDto findById(UUID id) throws EntityNotFoundException;

    UUID createCampaign(CampaignCreatorDto dto) throws CampaignInvalidException;

    UUID updateCampaign(CampaignDto dto) throws EntityNotFoundException;

    void deleteCampaign(UUID id) throws EntityNotFoundException;

}
