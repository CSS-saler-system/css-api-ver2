package com.springframework.csscapstone.controller.collaborator;

import com.springframework.csscapstone.data.status.CampaignStatus;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.CampaignForCollaboratorResDto;
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
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Campaign.V3_GET_CAMPAIGN;
import static com.springframework.csscapstone.config.constant.ApiEndPoint.Campaign.V3_LIST_CAMPAIGN;
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
            @RequestParam(value = "minKpi", required = false, defaultValue = "0") Long minKpi,
            @RequestParam(value = "status", required = false) CampaignStatus status,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        PageImplResDto<CampaignForCollaboratorResDto> result = campaignService
                .listCampaignWithoutEnterpriseIdForCollaborator(campaignName, startDate, minKpi, status, pageNumber, pageSize);
        return ResponseEntity.ok(result);
    }

    @GetMapping(V3_GET_CAMPAIGN + "/{campaignId}")
    public ResponseEntity<?> getCampaignById(@PathVariable("campaignId") UUID id) throws EntityNotFoundException {
        return ok(campaignService.findById(id));
    }
}
