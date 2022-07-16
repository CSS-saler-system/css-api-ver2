package com.springframework.csscapstone.controller.collaborator;

import com.azure.core.annotation.Get;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResDto;
import com.springframework.csscapstone.services.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Account.V3_ENTERPRISE_ID;
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

    @GetMapping(V3_ENTERPRISE_ID + "/{enterpriseId}")
    public ResponseEntity<?> getEnterpriseWithProduct(
            @PathVariable("enterpriseId") UUID enterpriseId) throws AccountNotFoundException {
        AccountResDto result = this.accountService.getById(enterpriseId);
        return ok(result);
    }

//    @GetMapping()

}
