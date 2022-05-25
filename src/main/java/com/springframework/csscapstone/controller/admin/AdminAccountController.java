package com.springframework.csscapstone.controller.admin;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.config.constant.RegexConstant;
import com.springframework.csscapstone.payload.basic.AccountDto;
import com.springframework.csscapstone.payload.request_dto.admin.AccountCreatorDto;
import com.springframework.csscapstone.payload.response_dto.PageAccountDto;
import com.springframework.csscapstone.payload.sharing.AccountUpdaterDto;
import com.springframework.csscapstone.services.AccountService;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountExistException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Account.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Account (Admin)")
public class AdminAccountController {
    private final AccountService service;

    @GetMapping(V1_LIST_ACCOUNT)
    public ResponseEntity<?> getListDto(
            @RequestParam(value = "accountName", required = false) String accountName,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "page_number", required = false) Integer pageNumber,
            @RequestParam(value = "page_size", required = false) Integer pageSize
    ) {
        PageAccountDto page = service.getAllDto(accountName, phone, email, address, pageSize, pageNumber);
        return ResponseEntity.ok(page);
    }

    @GetMapping(V1_GET_ACCOUNT + "/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable("id") UUID id)
            throws AccountInvalidException, AccountNotFoundException {
        return ok(service.getById(id));
    }

    @PutMapping(V1_UPDATE_ACCOUNT)
    public ResponseEntity<UUID> updateAccount(@Valid @RequestBody AccountUpdaterDto dto) throws AccountInvalidException {
        UUID accountUUID = service.updateAccount(dto);
        return ok(accountUUID);
    }

    @PostMapping(V1_CREATE_ACCOUNT)
    public ResponseEntity<UUID> addNewAccount(@RequestBody AccountCreatorDto dto)
            throws AccountExistException, AccountNotFoundException {
        if (!dto.getRole().matches(RegexConstant.REGEX_ROLE) || dto.getRole() == null) {
            dto.setRole("ROLE_3");
        }
        UUID account = service.createAccount(dto);
        return ok(account);
    }

    @DeleteMapping(V1_DELETE_ACCOUNT + "/{id}")
    public ResponseEntity<String> disableAccount(@PathVariable("id") UUID id) {
        service.disableAccount(id);
        return ResponseEntity.ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }
}
