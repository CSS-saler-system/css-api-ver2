package com.springframework.csscapstone.controller.admin;

import com.springframework.csscapstone.config.constant.DataConstraint;
import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.services.business.CampaignService;
import com.springframework.csscapstone.services.model_dto.basic.CampaignDto;
import com.springframework.csscapstone.services.model_dto.custom.creator_model.CampaignCreatorDto;
import com.springframework.csscapstone.data.status.CampaignStatus;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.campaign_exception.CampaignInvalidException;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Campaign.*;
import static com.springframework.csscapstone.utils.request_utils.RequestUtils.getRequestParam;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@Tag(name = "Campaign (Admin)")
@RequiredArgsConstructor
public class AdminCampaignController {

    private final CampaignService campaignService;

    @GetMapping(V1_LIST_CAMPAIGN)
    public ResponseEntity<List<CampaignDto>> getListDto(
            @RequestParam(value = "campaignName", required = false) String campaignName,
            @RequestParam(value = "createdDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdDate,
            @RequestParam(value = "lastModifiedDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastModifiedDate,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "kpi", required = false, defaultValue = "0") Long kpi,
            @RequestParam(value = "campaignStatus", required = false, defaultValue = "FINISHED") CampaignStatus status
    ) {
        campaignName = getRequestParam(campaignName);
        description = getRequestParam(description);
        kpi = (Objects.nonNull(kpi) && kpi >= 0) ? kpi : 0;
        createdDate = Objects.nonNull(createdDate) ? createdDate : DataConstraint.MIN_DATE;
        lastModifiedDate = Objects.nonNull(lastModifiedDate) ? lastModifiedDate : DataConstraint.MAX_DATE;
        startDate = Objects.nonNull(startDate) ? startDate : createdDate;
        endDate = Objects.nonNull(endDate) ? endDate : DataConstraint.MAX_DATE;
        status = Objects.nonNull(status) ? status : CampaignStatus.FINISHED;

        List<CampaignDto> CampaignDto = campaignService.findCampaign(campaignName, createdDate,
                lastModifiedDate, startDate, endDate, description, kpi, status);
        return ResponseEntity.ok(CampaignDto);
    }

    @GetMapping(V1_GET_CAMPAIGN + "/{id}")
    public ResponseEntity<CampaignDto> getCampaignById(@PathVariable("id") UUID id) throws EntityNotFoundException {
        return ok(campaignService.findById(id));
    }

    @PutMapping(V1_UPDATE_CAMPAIGN)
    public ResponseEntity<UUID> updateCampaign(@RequestBody CampaignDto dto) throws EntityNotFoundException {
        UUID campaignUUID = campaignService.updateCampaign(dto);
        return ok(campaignUUID);
    }

    @PostMapping(V1_CREATE_CAMPAIGN)
    public ResponseEntity<UUID> addNewCampaign(@RequestBody CampaignCreatorDto dto) throws CampaignInvalidException {
        UUID campaign = campaignService.createCampaign(dto);
        return ok(campaign);
    }

    @DeleteMapping(V1_DELETE_CAMPAIGN + "/{id}")
    public ResponseEntity<String> disableCampaign(@PathVariable("id") UUID id) throws EntityNotFoundException {
        campaignService.deleteCampaign(id);
        return ResponseEntity.ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }

}
