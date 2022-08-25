package com.springframework.csscapstone.controller.enterprise;

import com.springframework.csscapstone.services.EnterpriseInformationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.Enterprise.*;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Home Information (Enterprise)")
@RestController
@RequiredArgsConstructor
public class EnterpriseCountInformationController {
    private final EnterpriseInformationService enterpriseInformationService;


    @GetMapping(V2_COLLABORATOR_COUNT + "/{enterpriseId}")
    public ResponseEntity<?> getCountOfCollaboratorByEnterprise(@PathVariable("enterpriseId") UUID enterpriseId) {
        long count = enterpriseInformationService.countCollaborator(enterpriseId);
        return ok(count);
    }


    @GetMapping(V2_INFORMATION_REQUEST + "/{enterpriseId}")
    public ResponseEntity<?> getCountOfRequestByEnterprise(@PathVariable("enterpriseId") UUID enterpriseId) {
        long count = enterpriseInformationService.countRequestSeliingProduct(enterpriseId);
        return ok(count);
    }

    @GetMapping(V2_INFORMATION_POINT + "/{enterpriseId}")
    public ResponseEntity<?> getPointInformation(@PathVariable("enterpriseId") UUID enterpriseId) {
        Double point = enterpriseInformationService.getPoint(enterpriseId);
        return ok(point);
    }

}
