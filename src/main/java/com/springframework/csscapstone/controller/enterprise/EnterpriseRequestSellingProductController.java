package com.springframework.csscapstone.controller.enterprise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springframework.csscapstone.data.status.RequestStatus;
import com.springframework.csscapstone.payload.response_dto.enterprise.RequestSellingProductResDto;
import com.springframework.csscapstone.services.RequestSellingProductService;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.RequestSellingProduct.*;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Request_Selling (Enterprise)")
@RestController
@RequiredArgsConstructor
public class EnterpriseRequestSellingProductController {

    private final RequestSellingProductService requestSellingProductService;
    private final Function<UUID, Supplier<EntityNotFoundException>> entityNotFoundExceptionSupplier =
            (idRequest) -> () -> new EntityNotFoundException("The request selling with id: " + idRequest + "not found ");

    @GetMapping(V2_REQUEST_LIST + "/{enterpriseId}")
    public ResponseEntity<?> getListRequestSellingProduct(
            @PathVariable("enterpriseId") UUID enterpriseId,
            @RequestParam(value = "status", required = false) RequestStatus status,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber
    ) {
        return ok(requestSellingProductService
                .getAllRequestByStatusAndEnterprise(enterpriseId, status, pageNumber, pageSize));
    }

    @GetMapping(V2_REQUEST_GET + "/{requestId}")
    public ResponseEntity<?> getRequestSellingProduct(@PathVariable("requestId") UUID requestId) {
        RequestSellingProductResDto request = this.requestSellingProductService
                .getRequestById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("The request with id: " + requestId + " was not found"));
        return ok(request);
    }

    @PutMapping(V2_REQUEST_UPDATE + "/{requestId}")
    public ResponseEntity<?> updateStatusRequest(
            @PathVariable("requestId") UUID idRequest,
            @RequestParam(value = "status") RequestStatus status)
            throws ExecutionException, JsonProcessingException, InterruptedException {
        UUID id = this.requestSellingProductService
                .updateRequestSellingProduct(idRequest, status)
                .orElseThrow(entityNotFoundExceptionSupplier.apply(idRequest));
        return ok(id);
    }

}

