package com.springframework.csscapstone.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springframework.csscapstone.data.status.CampaignStatus;
import com.springframework.csscapstone.payload.request_dto.admin.CampaignCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.CampaignUpdaterReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.CampaignDetailDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.CampaignResDto;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.campaign_exception.CampaignInvalidException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface CampaignService {

    //    List<CampaignResDto> findCampaign(
//            String name, LocalDateTime createdDate,
//            LocalDateTime lastModifiedDate,
//            LocalDateTime startDate,
//            LocalDateTime endDate,
//            String description,
//            Long kpi,
//            CampaignStatus status);
    PageImplResDto<CampaignResDto> findCampaign(
            UUID enterpriseId,
            String name,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Long minKpi, Long maxKpi, CampaignStatus status,
            Integer pageNumber, Integer pageSize);

    PageImplResDto<CampaignResDto> findCampaignWithoutEnterpriseId(
            String name, LocalDateTime date, Long kpi,
            CampaignStatus status, Integer pageNumber, Integer pageSize);

    CampaignDetailDto findById(UUID id) throws EntityNotFoundException;

    UUID createCampaign(CampaignCreatorReqDto dto, List<MultipartFile> images) throws CampaignInvalidException;

    UUID updateCampaign(CampaignUpdaterReqDto dto, List<MultipartFile> images) throws EntityNotFoundException;

    void deleteCampaign(UUID id) throws EntityNotFoundException;

    void scheduleCloseCampaign();

    //todo close campaign
    void completeCampaign(UUID id) throws JsonProcessingException;

    void rejectCampaignInDate();

    void updateStatusCampaignForModerator(
            UUID campaignId, CampaignStatus status);
}
