package com.springframework.csscapstone.controller.enterprise;

import com.springframework.csscapstone.payload.response_dto.PageImplResponse;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResponseDto;
import com.springframework.csscapstone.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Account.V2_LIST_ACCOUNT;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class EnterpriseCollaboratorsController {

    private final AccountService accountService;

    @GetMapping(V2_LIST_ACCOUNT + "/{idEnterprise}")
    public ResponseEntity<?> getCollaborator(
            @PathVariable("idEnterprise") UUID id,
            @RequestParam("page_number") Integer pageNumber,
            @RequestParam("page_size") Integer pageSize) {

        PageImplResponse<AccountResponseDto> allCollaboratorsOfEnterprise =
                this.accountService.getAllCollaboratorsOfEnterprise(id, pageNumber, pageSize);
        return ok(allCollaboratorsOfEnterprise);
    }
}
