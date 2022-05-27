package com.springframework.csscapstone.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.payload.request_dto.admin.AccountCreatorDto;
import com.springframework.csscapstone.payload.response_dto.PageAccountDto;
import com.springframework.csscapstone.payload.sharing.AccountUpdaterDto;
import com.springframework.csscapstone.services.AccountService;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountExistException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Account.*;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
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
        PageAccountDto page = service.getAccountDto(accountName, phone, email, address, pageSize, pageNumber);
        return ResponseEntity.ok(page);
    }

    @GetMapping(V1_GET_ACCOUNT + "/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable("id") UUID id)
            throws AccountInvalidException, AccountNotFoundException {
        return ok(service.getById(id));
    }

    /**
     * todo update account by admin
     * @return
     * @throws AccountInvalidException
     */
    @PutMapping(value = V1_UPDATE_ACCOUNT, consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UUID> updateAccount(
            @RequestPart("account") String dto,
            @RequestPart(value = "avatar", required = false)  MultipartFile avatars,
            @RequestPart(value = "license", required = false) MultipartFile licenses,
            @RequestPart(value = "id_card", required = false) MultipartFile idCards

    ) throws AccountInvalidException, JsonProcessingException {
        AccountUpdaterDto accountUpdaterDto = new ObjectMapper().readValue(dto, AccountUpdaterDto.class);
        return ok(this.service.updateAccount(accountUpdaterDto, avatars, licenses, idCards));
    }

    /**
     * TODO Multipart transfer images avatars
     * @param dto
     * @return
     * @throws AccountExistException
     * @throws AccountNotFoundException
     */
    @PostMapping(value = V1_CREATE_ACCOUNT, consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UUID> addNewAccount(
            @RequestPart("account") String dto,
            @RequestPart(value = "avatar", required = false)  MultipartFile avatars,
            @RequestPart(value = "license", required = false) MultipartFile licenses,
            @RequestPart(value = "id_card", required = false) MultipartFile idCards)
            throws AccountExistException, AccountNotFoundException, JsonProcessingException {
        AccountCreatorDto accountCreatorDto = new ObjectMapper().readValue(dto, AccountCreatorDto.class);
        UUID account = service.createAccount(accountCreatorDto, avatars, licenses, idCards);
        return ok(account);
    }

    /**
     * TODO Admin disable account entity
     * @param id
     * @return
     */
    @DeleteMapping(V1_DELETE_ACCOUNT + "/{id}")
    public ResponseEntity<String> disableAccount(@PathVariable("id") UUID id) {
        service.disableAccount(id);
        return ResponseEntity.ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }
}
