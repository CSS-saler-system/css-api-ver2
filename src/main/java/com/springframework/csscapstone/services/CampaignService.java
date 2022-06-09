package com.springframework.csscapstone.services;

import com.springframework.csscapstone.data.status.CampaignStatus;
import com.springframework.csscapstone.payload.basic.CampaignBasicDto;
import com.springframework.csscapstone.payload.request_dto.admin.CampaignCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.CampaignUpdaterReqDto;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.campaign_exception.CampaignInvalidException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CampaignService {

    List<CampaignBasicDto> findCampaign(
            String name, LocalDateTime createdDate,
            LocalDateTime lastModifiedDate,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String description,
            Long kpi,
            CampaignStatus status);

    CampaignBasicDto findById(UUID id) throws EntityNotFoundException;

    UUID createCampaign(CampaignCreatorReqDto dto, List<MultipartFile> images) throws CampaignInvalidException;

    UUID updateCampaign(CampaignUpdaterReqDto dto, List<MultipartFile> images) throws EntityNotFoundException;

    void deleteCampaign(UUID id) throws EntityNotFoundException;

    void scheduleCloseCampaign();

    //todo close campaign
    void completeCampaign(UUID id);

}
