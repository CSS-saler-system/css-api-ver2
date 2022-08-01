package com.springframework.csscapstone.controller.collaborator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springframework.csscapstone.data.status.RequestStatus;
import com.springframework.csscapstone.payload.request_dto.collaborator.RequestSellingProductCreatorDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.RequestSellingProductResDto;
import com.springframework.csscapstone.services.RequestSellingProductService;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.RequestSellingProduct.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Request_Selling (Collaborator)")
public class CollaboratorRequestController {
    private final RequestSellingProductService requestSellingProductService;

    @PostMapping(V3_REQUEST_CREATE)
    public ResponseEntity<?> createRequestSellingProduct(
            @RequestBody RequestSellingProductCreatorDto dto
    ) throws AccountNotFoundException, ExecutionException,
            JsonProcessingException, InterruptedException {
        UUID id = requestSellingProductService.createRequestSellingProduct(dto);
        return ok(id);
    }

    @GetMapping(V3_REQUEST_GET_OWNER + "/{collaboratorId}")
    public ResponseEntity<?> getOwnerRequestSellingProduct(
            @PathVariable("collaboratorId") UUID idCollaborator,
            @RequestParam(value = "requestStatus") RequestStatus status,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        PageImplResDto<RequestSellingProductResDto> allRequestByIdCreator =
                this.requestSellingProductService
                        .getAllRequestByIdCreatorByCollaborator(idCollaborator, status, pageNumber, pageSize);
        return ok(allRequestByIdCreator);
    }

    @GetMapping(V3_REQUEST_RETRIEVE + "/{requestId}")
    public ResponseEntity<?> getRequestById(@PathVariable("requestId") UUID idRequest) {
        RequestSellingProductResDto request = this.requestSellingProductService.getRequestById(idRequest)
                .orElseThrow(() -> new EntityNotFoundException("The Request with: " + idRequest + "not found"));
        return ok(request);
    }

}
