package com.springframework.csscapstone.controller.collaborator;

import com.azure.core.annotation.Get;
import com.springframework.csscapstone.services.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Account.V3_LIST_ACCOUNT;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Enterprise (Collaborator)")
@RestController
@RequiredArgsConstructor
public class CollaboratorEnterpriseController {
    private final AccountService accountService;

    @GetMapping(V3_LIST_ACCOUNT)
    public ResponseEntity<?> getListAccountEnterprise() {
//        return ok(accountService.getAllHavingEnterpriseRole());
        return null;
    }

}
