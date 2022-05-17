package com.springframework.csscapstone.controller.admin;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.config.constant.RegexConstant;
import com.springframework.csscapstone.services.AccountService;
import com.springframework.csscapstone.payload.basic.AccountDto;
import com.springframework.csscapstone.payload.basic.RoleDto;
import com.springframework.csscapstone.payload.custom.creator_model.AccountRegisterDto;
import com.springframework.csscapstone.payload.custom.update_model.AccountUpdaterDto;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountExistException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Account.*;
import static com.springframework.csscapstone.utils.request_utils.RequestUtils.getRequestParam;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Account (Admin)")
public class AdminAccountController {
    private final AccountService service;

    @GetMapping(V1_LIST_ACCOUNT)
    public ResponseEntity<List<AccountDto>> getListDto(
            @RequestParam(value = "accountName", required = false) String accountName,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "accountStatus", required = false, defaultValue = "True") String status
    ) {
        //check null values
        accountName = getRequestParam(accountName);
        phone = getRequestParam(phone);
        email = getRequestParam(email);
        address = getRequestParam(address);
        description = getRequestParam(description);
        boolean _status = Objects.equals(status, "True");

        List<AccountDto> accountList = service.getAllDto(accountName, phone, email, address, description, _status);
        return ResponseEntity.ok(accountList);
    }

    @GetMapping(V1_GET_ACCOUNT + "/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable("id") UUID id)
            throws AccountInvalidException {
        return ok(service.getById(id));
    }

    @PutMapping(V1_UPDATE_ACCOUNT)
    public ResponseEntity<UUID> updateAccount(@RequestBody AccountUpdaterDto dto) throws AccountInvalidException {
        UUID accountUUID = service.updateAccount(dto);
        return ok(accountUUID);
    }

    @PostMapping(V1_CREATE_ACCOUNT)
    public ResponseEntity<UUID> addNewAccount(@RequestBody AccountRegisterDto dto) throws AccountExistException {

        if (!dto.getRole().getId().matches(RegexConstant.REGEX_ROLE) || dto.getRole() == null) {
            dto.setRole(new RoleDto("Role_3", "Collaborator"));
        }

        UUID account = service.registerAccount(dto);
        return ok(account);
    }

    @DeleteMapping(V1_DELETE_ACCOUNT + "/{id}")
    public ResponseEntity<String> disableAccount(@PathVariable("id") UUID id) {
        service.disableAccount(id);
        return ResponseEntity.ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }
}
