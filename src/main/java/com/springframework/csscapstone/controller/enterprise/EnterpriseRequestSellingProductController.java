package com.springframework.csscapstone.controller.enterprise;

import com.springframework.csscapstone.data.status.RequestStatus;
import com.springframework.csscapstone.services.RequestSellingProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.RequestSellingProduct.V2_LIST_REQUEST;
import static com.springframework.csscapstone.config.constant.ApiEndPoint.RequestSellingProduct.V2_UPDATE_REQUEST;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Request_Selling (Enterprise)")
@RestController
@RequiredArgsConstructor
public class EnterpriseRequestSellingProductController {

    private final RequestSellingProductService requestSellingProductService;

    @GetMapping(V2_LIST_REQUEST + "/{enterpriseId}")
    public ResponseEntity<?> getListRequestSellingProduct(
            @PathVariable("enterpriseId") UUID enterpriseId,
            @RequestParam(value = "status", required = false) RequestStatus status,
            @RequestParam(value = "page_size", required = false) Integer pageSize,
            @RequestParam(value = "page_number", required = false) Integer pageNumber
    ) {
        return ok(requestSellingProductService.getAllRequestByStatus(enterpriseId, status, pageSize, pageNumber));
    }

    @PutMapping(V2_UPDATE_REQUEST + "/{id}")
    public ResponseEntity<?> updateStatusRequest(
            @PathVariable("id") UUID idRequest,
            @RequestParam(value = "status") RequestStatus status) {
        return ok(this.requestSellingProductService.updateProduct(idRequest, status));
    }

}
