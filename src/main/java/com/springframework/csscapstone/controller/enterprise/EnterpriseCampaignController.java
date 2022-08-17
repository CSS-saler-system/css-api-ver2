package com.springframework.csscapstone.controller.enterprise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.config.message.constant.MessageConstant;
import com.springframework.csscapstone.data.status.CampaignStatus;
import com.springframework.csscapstone.payload.request_dto.admin.CampaignCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.CampaignUpdaterReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.CampaignDetailDto;
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
import java.util.UUID;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.Campaign.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Campaign (Enterprise)")
public class EnterpriseCampaignController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CampaignService campaignService;


    @PostMapping(
            value = V2_CAMPAIGN_CREATE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UUID> addNewCampaign(
            @RequestPart(name = "campaign") String campaignCreatorReqDto,
            @RequestPart(name = "images") List<MultipartFile> images)
            throws CampaignInvalidException, JsonProcessingException {

        CampaignCreatorReqDto dto = this.objectMapper.readValue(campaignCreatorReqDto, CampaignCreatorReqDto.class);

//        if (dto.getKpi() < 0) throw new RuntimeException("The KPI must be greater than 0");
//
//        if (dto.getStartDate().atStartOfDay().isBefore(LocalDateTime.now().plusDays(3)))
//            throw new RuntimeException("The start day must be after 3 days from now");
//
//        if (dto.getStartDate().isAfter(dto.getEndDate()))
//            throw new RuntimeException("The start, end date is invalid");

        UUID campaign = campaignService.createCampaign(dto, images);
        return ok(campaign);
    }

    @GetMapping(V2_CAMPAIGN_LIST + "/{enterpriseId}")
    public ResponseEntity<?> getListDto(
            @PathVariable("enterpriseId") UUID enterpriseId,
            @RequestParam(value = "campaignName", required = false) String campaignName,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(value = "minKpi", required = false) Long minKpi,
            @RequestParam(value = "maxKpi", required = false) Long maxKpi,
            @RequestParam(value = "status", required = false) CampaignStatus status,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        PageImplResDto<CampaignResDto> campaign = campaignService.findCampaign(
                enterpriseId, campaignName, startDate, endDate, minKpi, maxKpi, status, pageNumber, pageSize);
        return ResponseEntity.ok(campaign);
    }


    @PutMapping(value = V2_CAMPAIGN_UPDATE + "/{campaignId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UUID> updateCampaign(
            @PathVariable("campaignId") UUID campaignId,
            @RequestPart("campaign") String campaign,
            @RequestPart(value = "images", required = false) List<MultipartFile> images)
            throws EntityNotFoundException, JsonProcessingException {
        CampaignUpdaterReqDto dto = objectMapper
                .readValue(campaign, CampaignUpdaterReqDto.class);

//        //campaignNotFoundException
//        if (dto.getKpiSaleProduct() < 0) throw new RuntimeException("The KPI must be greater than 0");
//
//        if (dto.getStartDate().isBefore(LocalDateTime.now().plusDays(3)))
//            throw new RuntimeException("The start day must be after 3 days from now");
//
//        if (dto.getStartDate().isAfter(dto.getEndDate()))
//            throw new RuntimeException("The start, end date is invalid");

        UUID campaignUUID = campaignService.updateCampaign(campaignId, dto, images);
        return ok(campaignUUID);
    }

    @PutMapping(value = V2_CAMPAIGN_TEST + "/{campaignId}")
    public ResponseEntity<?> updateClosingCampaign(@PathVariable("campaignId") UUID campaignId)
            throws EntityNotFoundException, JsonProcessingException {
        this.campaignService.completeCampaign(campaignId);
        return ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }

    @GetMapping(V2_CAMPAIGN_GET + "/{campaignId}")
    public ResponseEntity<?> getCampaignById(@PathVariable("campaignId") UUID campaignId) {
        CampaignDetailDto result = this.campaignService.findById(campaignId);
        return ok(result);
    }

    @GetMapping(V2_CAMPAIGN_GET + "/detail/{campaignId}")
    public ResponseEntity<?> getDetailCampaignDetail(@PathVariable("campaignId") UUID campaignId) {
        return ok(this.campaignService.getInformationCompletedCampaign(campaignId));
    }

    @PutMapping(V2_CAMPAIGN_SENT + "/{campaignId}")
    public ResponseEntity<?> sentCampaign(@PathVariable("campaignId") UUID campaignId) {
        this.campaignService.sentCampaign(campaignId);
        return ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }

    @DeleteMapping(V2_CAMPAIGN_DELETE + "/{campaignId}")
    public ResponseEntity<?> deleteCampaign(@PathVariable("campaignId") UUID campaignId) {
        this.campaignService.deleteCampaign(campaignId);
        return ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }

}
