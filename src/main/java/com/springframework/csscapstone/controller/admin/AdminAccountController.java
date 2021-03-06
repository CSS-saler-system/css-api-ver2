package com.springframework.csscapstone.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuthException;
import com.springframework.csscapstone.config.message.constant.MessageConstant;
import com.springframework.csscapstone.payload.request_dto.admin.AccountCreatorReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResDto;
import com.springframework.csscapstone.payload.sharing.AccountUpdaterJsonDto;
import com.springframework.csscapstone.services.AccountService;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountExistException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.Account.*;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Account (Admin)")
@RestController
@RequiredArgsConstructor
public class AdminAccountController {
    private final AccountService service;

    @GetMapping(V1_LIST_ACCOUNT)
    public ResponseEntity<?> getListDto(
            @RequestParam(value = "accountName", required = false) String accountName,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        PageImplResDto<AccountResDto> page = service.getAccountDto(accountName, phone, email, address, pageSize, pageNumber);
        return ResponseEntity.ok(page);
    }

    @GetMapping(V1_GET_ACCOUNT + "/{accountId}")
    public ResponseEntity<?> getAccountById(@PathVariable("accountId") UUID id)
            throws AccountInvalidException, AccountNotFoundException {
        return ok(service.getById(id));
    }

    /**
     * todo update account by admin
     *
     * @return
     * @throws AccountInvalidException
     */
    @PutMapping(
            value = V1_UPDATE_ACCOUNT + "/{accountId}",
            consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UUID> updateAccount(
            @PathVariable(value = "accountId") UUID accountId,
            @RequestPart(value = "accountDto") String accountDto,
            @RequestPart(value = "avatars", required = false) MultipartFile avatars,
            @RequestPart(value = "licenses", required = false) MultipartFile licenses,
            @RequestPart(value = "idCards", required = false) MultipartFile idCards

    ) throws AccountInvalidException, JsonProcessingException {
//        System.out.println("Im inside");
        AccountUpdaterJsonDto accountUpdaterJsonDto = new ObjectMapper().readValue(accountDto, AccountUpdaterJsonDto.class);
        return ok(this.service.updateAccount(accountId, accountUpdaterJsonDto, avatars, licenses, idCards));
//        return null;
    }

    /**
     * TODO Multipart transfer images avatars
     *
     * @param dto
     * @return
     * @throws AccountExistException
     * @throws AccountNotFoundException
     */
    @PostMapping(value = V1_CREATE_ACCOUNT, consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UUID> addNewAccount(
            @RequestPart(value = "avatar", required = false) MultipartFile avatars,
            @RequestPart(value = "license", required = false) MultipartFile licenses,
            @RequestPart(value = "idCard", required = false) MultipartFile idCards,
            @RequestPart(value = "account") String dto)
            throws AccountExistException, AccountNotFoundException, JsonProcessingException, FirebaseAuthException {
        AccountCreatorReqDto accountCreatorReqDto = new ObjectMapper().readValue(dto, AccountCreatorReqDto.class);
        UUID account = service.createEnterpriseAccount(accountCreatorReqDto, avatars, licenses, idCards);
        return ok(account);
    }

    /**
     * TODO Admin disable account entity
     *
     * @param id
     * @return
     */
    @DeleteMapping(V1_DELETE_ACCOUNT + "/{accountId}")
    public ResponseEntity<String> disableAccount(@PathVariable("accountId") UUID id) {
        service.disableAccount(id);
        return ResponseEntity.ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }
}
