package com.springframework.csscapstone.controller.moderator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuthException;
import com.springframework.csscapstone.payload.request_dto.admin.AccountCreatorReqDto;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResDto;
import com.springframework.csscapstone.payload.sharing.AccountUpdaterJsonDto;
import com.springframework.csscapstone.services.AccountService;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountExistException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Account.*;
import static com.springframework.csscapstone.config.constant.RegexConstant.EMAIL_REGEX;
import static com.springframework.csscapstone.config.constant.RegexConstant.PHONE_REGEX;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@Tag(name = "Account (Moderator)")
@RequiredArgsConstructor
public class ModeratorAccountController {
    private final AccountService accountService;

    /**
     * todo update account by admin
     *
     * @return
     * @throws AccountInvalidException
     */
    @PutMapping(value = V4_ACCOUNT_UPDATE + "/{accountId}", consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UUID> updateAccount(
            @PathVariable("accountId") UUID accountId,
            @RequestPart("account") String dto,
            @RequestPart(value = "avatar", required = false) MultipartFile avatars,
            @RequestPart(value = "license", required = false) MultipartFile licenses,
            @RequestPart(value = "idCard", required = false) MultipartFile idCards

    ) throws AccountInvalidException, JsonProcessingException {
        AccountUpdaterJsonDto accountUpdaterJsonDto = new ObjectMapper().readValue(dto, AccountUpdaterJsonDto.class);
        return ok(this.accountService.updateAccount(accountId, accountUpdaterJsonDto, avatars, licenses, idCards));
    }

    /**
     * TODO Multipart transfer images avatars
     *
     * @param dto
     * @return
     * @throws AccountExistException
     * @throws AccountNotFoundException
     */
    @PostMapping(value = V4_ACCOUNT_CREATE, consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UUID> addNewAccount(
            @RequestPart("account") String dto,
            @RequestPart(value = "avatar", required = false) MultipartFile avatars,
            @RequestPart(value = "license", required = false) MultipartFile licenses,
            @RequestPart(value = "idCard", required = false) MultipartFile idCards)
            throws AccountExistException, AccountNotFoundException, JsonProcessingException, FirebaseAuthException {
        AccountCreatorReqDto accountCreatorReqDto = new ObjectMapper().readValue(dto, AccountCreatorReqDto.class);

        if (!accountCreatorReqDto.getEmail().matches(EMAIL_REGEX)) {
            throw new RuntimeException("The email was not follow format");
        }

        if (!accountCreatorReqDto.getPhone().matches(PHONE_REGEX)) {
            System.out.println(accountCreatorReqDto.getPhone());
            throw new RuntimeException("The phone was not follow format, length 10");
        }
        //create enterprise
        UUID account = accountService.createEnterpriseAccount(accountCreatorReqDto, avatars, licenses, idCards);
        return ok(account);
    }

    /**
     * todo Get Account by Id
     *
     * @param accountId
     * @return
     * @throws AccountNotFoundException
     */
    @GetMapping(V4_ACCOUNT_GET + "/{accountId}")
    public ResponseEntity<?> getAccountById(@PathVariable("accountId") UUID accountId) throws AccountNotFoundException {
        AccountResDto account = this.accountService.getById(accountId);
        return ok(account);
    }

}
