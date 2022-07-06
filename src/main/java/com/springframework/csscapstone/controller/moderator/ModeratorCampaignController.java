package com.springframework.csscapstone.controller.moderator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.config.constant.DataConstraint;
import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.status.CampaignStatus;
import com.springframework.csscapstone.payload.request_dto.admin.CampaignCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.CampaignUpdaterReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.CampaignResDto;
import com.springframework.csscapstone.services.CampaignService;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.campaign_exception.CampaignInvalidException;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Campaign.*;
import static com.springframework.csscapstone.utils.request_utils.RequestUtils.getRequestParam;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@Tag(name = "Campaign (Moderator)")
@RequiredArgsConstructor
public class ModeratorCampaignController {

    private final CampaignService campaignService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(V4_LIST_CAMPAIGN)
    public ResponseEntity<?> getListDto(
            @RequestParam(value = "campaignName", required = false) String campaignName,
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "minKpi", required = false) Long minKpi,
            @RequestParam(value = "status", required = false) CampaignStatus status,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        PageImplResDto<CampaignResDto> campaign = campaignService
                .findCampaignWithoutEnterpriseId(campaignName, startDate, minKpi, status, pageNumber, pageSize);
        return ResponseEntity.ok(campaign);
    }

    @GetMapping(V4_GET_CAMPAIGN + "/{campaignId}")
    public ResponseEntity<?> getCampaignById(@PathVariable("campaignId") UUID id) throws EntityNotFoundException {
        return ok(campaignService.findById(id));
    }

    @PutMapping(V4_REJECT_CAMPAIGN + "/{campaignId}")
    public ResponseEntity<?> rejectCampaign(@PathVariable("campaignId") UUID campaignID) {
        this.campaignService.updateStatusCampaignForModerator(campaignID, CampaignStatus.REJECTED);
        return ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }

    @PutMapping(V4_APPROVAL_CAMPAIGN + "/{campaignId}")
    public ResponseEntity<?> approvalCampaign(@PathVariable("campaignId") UUID campaignID) {
        this.campaignService.updateStatusCampaignForModerator(campaignID, CampaignStatus.APPROVAL);
        return ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }
}
