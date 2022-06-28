package com.springframework.csscapstone.controller.collaborator;

import com.springframework.csscapstone.config.constant.DataConstraint;
import com.springframework.csscapstone.data.status.CampaignStatus;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.CampaignResDto;
import com.springframework.csscapstone.services.CampaignService;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Campaign.V3_GET_CAMPAIGN;
import static com.springframework.csscapstone.config.constant.ApiEndPoint.Campaign.V3_LIST_CAMPAIGN;
import static com.springframework.csscapstone.utils.request_utils.RequestUtils.getRequestParam;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@Tag(name = "Campaign (Collaborator)")
@RequiredArgsConstructor
public class CollaboratorCampaignController {

    private final CampaignService campaignService;

    @GetMapping(V3_LIST_CAMPAIGN)
    public ResponseEntity<?> getListDto(
            @RequestParam(value = "campaignName", required = false) String campaignName,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(value = "minKpi", required = false, defaultValue = "0") Long minKpi,
            @RequestParam(value = "maxKpi", required = false, defaultValue = "0") Long maxKpi,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "maxSize", required = false, defaultValue = "0") Integer pageSize
    ) {
        PageImplResDto<CampaignResDto> campaign = campaignService.findCampaignWithoutEnterpriseId(
                campaignName, startDate, endDate, minKpi, maxKpi, CampaignStatus.PENDING,pageNumber, pageSize);
        return ResponseEntity.ok(campaign);
    }

    @GetMapping(V3_GET_CAMPAIGN + "/{campaignId}")
    public ResponseEntity<?> getCampaignById(@PathVariable("campaignId") UUID id) throws EntityNotFoundException {
        return ok(campaignService.findById(id));
    }
}
