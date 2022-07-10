package com.springframework.csscapstone.controller.enterprise;

import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.CollaboratorResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.CollaboratorWithQuantitySoldByCategoryDto;
import com.springframework.csscapstone.services.AccountService;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Account.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Collaborator (Enterprise)")
public class EnterpriseCollaboratorsController {

    private final AccountService accountService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @GetMapping(V2_COLLABORATOR_LIST + "/{enterpriseId}")
    public ResponseEntity<?> getCollaborator(
            @PathVariable("enterpriseId") UUID id,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {

        PageImplResDto<AccountResDto> allCollaboratorsOfEnterprise =
                this.accountService.getAllCollaboratorsOfEnterprise(id, pageNumber, pageSize);

        return ok(allCollaboratorsOfEnterprise);
    }

    @GetMapping(V2_ORDER_COLLABORATOR_LIST + "/{enterpriseId}")
    public ResponseEntity<?> getCollaboratorCounterOrder(
            @PathVariable("enterpriseId") UUID enterpriseId,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    )  {
        LOGGER.info("The message {}", enterpriseId);
        LOGGER.info("The page-number {}", pageNumber);
        LOGGER.info("The page-size {}", pageSize);
        PageImplResDto<CollaboratorResDto> result = this.accountService
                .collaboratorsByEnterpriseIncludeNumberOfQuantitySold(enterpriseId, pageNumber, pageSize);
        return ok(result);
    }

    @GetMapping(V2_COLLABORATOR_PERFORMANCE + "/{collaboratorId}")
    public ResponseEntity<?> getCollaboratorPerformance(
            @PathVariable("collaboratorId") UUID collaboratorId
    )  {
        CollaboratorWithQuantitySoldByCategoryDto result =
                this.accountService.getCollaboratorWithPerformance(collaboratorId)
                        .orElseThrow(() -> new EntityNotFoundException("Collaborator with id: " + collaboratorId +
                                " was not found!!!"));
        return ok(result);
    }

    @GetMapping(V2_COLLABORATOR_CAMPAIGN_LIST + "/{campaignId}")
    public ResponseEntity<?> listAllCollaboratorAfterCampaign(
            @PathVariable("campaignId") UUID campaignId
    ) {
        List<CollaboratorResDto> result = accountService.awardCollaboratorByCampaign(campaignId);
        return ok(result);
    }
}
