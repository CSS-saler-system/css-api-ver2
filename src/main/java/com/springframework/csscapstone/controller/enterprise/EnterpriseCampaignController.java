package com.springframework.csscapstone.controller.enterprise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.data.repositories.CampaignRepository;
import com.springframework.csscapstone.data.status.CampaignStatus;
import com.springframework.csscapstone.payload.request_dto.admin.CampaignCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.CampaignUpdaterReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.CampaignResDto;
import com.springframework.csscapstone.services.CampaignService;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.campaign_exception.CampaignInvalidException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Campaign.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Campaign (Enterprise)")
public class EnterpriseCampaignController {

    private final ObjectMapper objectMapper;
    private final CampaignService campaignService;


    @PostMapping(
            value = V2_CREATE_CAMPAIGN,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UUID> addNewCampaign(
            @RequestPart(name = "campaign") String campaignCreatorReqDto,
            @RequestPart(name = "images") List<MultipartFile> images)
            throws CampaignInvalidException, JsonProcessingException {
        CampaignCreatorReqDto dto = this.objectMapper.readValue(campaignCreatorReqDto, CampaignCreatorReqDto.class);
        if (dto.getKpi() < 0) throw new RuntimeException("The KPI must be greater than 0");
        UUID campaign = campaignService.createCampaign(dto, images);
        return ok(campaign);
    }

    @GetMapping(V2_LIST_CAMPAIGN)
    public ResponseEntity<?> getListDto(
            @RequestParam(value = "campaignName", required = false) String campaignName,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(value = "min_kpi", required = false, defaultValue = "0") Long minKpi,
            @RequestParam(value = "max_kpi", required = false, defaultValue = "0") Long maxKpi,
            @RequestParam(value = "status", required = false, defaultValue = "PENDING") CampaignStatus status,
            @RequestParam(value = "max_kpi", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "max_size", required = false, defaultValue = "0") Integer pageSize
    ) {
        PageImplResDto<CampaignResDto> campaign = campaignService.findCampaign(
                campaignName, startDate, endDate, minKpi, maxKpi,status, pageNumber, pageSize);
        return ResponseEntity.ok(campaign);
    }


    @PutMapping(value = V2_UPDATE_CAMPAIGN, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UUID> updateCampaign(
            @RequestPart("campaign") String campaign,
            @RequestPart("images") List<MultipartFile> images)
            throws EntityNotFoundException, JsonProcessingException {
        CampaignUpdaterReqDto dto = objectMapper
                .readValue(campaign, CampaignUpdaterReqDto.class);
        UUID campaignUUID = campaignService.updateCampaign(dto, images);
        return ok(campaignUUID);
    }


    /**
     * List All && find By account_id
     * Update Campaign
     */

}
