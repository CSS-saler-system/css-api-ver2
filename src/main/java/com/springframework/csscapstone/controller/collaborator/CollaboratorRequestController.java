package com.springframework.csscapstone.controller.collaborator;

import com.springframework.csscapstone.payload.request_dto.collaborator.RequestSellingProductCreatorDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.RequestSellingProductResDto;
import com.springframework.csscapstone.services.RequestSellingProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.RequestSellingProduct.V3_CREATE_REQUEST;
import static com.springframework.csscapstone.config.constant.ApiEndPoint.RequestSellingProduct.V3_GET_OWNER_REQUEST;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Request_Selling (Collaborator)")
public class CollaboratorRequestController {
    private final RequestSellingProductService requestSellingProductService;

    @PostMapping(V3_CREATE_REQUEST)
    public ResponseEntity<?> createRequestSellingProduct(
            @RequestBody RequestSellingProductCreatorDto dto
    ) throws AccountNotFoundException {
        UUID id = requestSellingProductService.createRequestSellingProduct(dto);
        return ok(id);
    }

    @GetMapping(V3_GET_OWNER_REQUEST + "/{idCollaborator}")
    public ResponseEntity<?> getOwnerRequestSellingProduct(
            @PathVariable("idCollaborator") UUID idCollaborator,
            @RequestParam("page_number") Integer pageNumber,
            @RequestParam("page_size") Integer pageSize) {
        PageImplResDto<RequestSellingProductResDto> allRequestByIdCreator =
                this.requestSellingProductService
                .getAllRequestByIdCreator(idCollaborator, pageNumber, pageSize);
        return ok(allRequestByIdCreator);
    }

}
