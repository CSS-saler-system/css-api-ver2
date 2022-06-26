package com.springframework.csscapstone.controller.moderator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuthException;
import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.payload.request_dto.admin.AccountCreatorReqDto;
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

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Account.*;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@Tag(name = "Account (Moderator)")
@RequiredArgsConstructor
public class ModeratorAccountController {
    private final AccountService accountService;

    /**
     * todo update account by admin
     * @return
     * @throws AccountInvalidException
     */
    @PutMapping(value = V4_UPDATE_ACCOUNT, consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UUID> updateAccount(
            @RequestPart("account") String dto,
            @RequestPart(value = "avatar", required = false) MultipartFile avatars,
            @RequestPart(value = "license", required = false) MultipartFile licenses,
            @RequestPart(value = "idCard", required = false) MultipartFile idCards

    ) throws AccountInvalidException, JsonProcessingException {
        AccountUpdaterJsonDto accountUpdaterJsonDto = new ObjectMapper().readValue(dto, AccountUpdaterJsonDto.class);
        return ok(this.accountService.updateAccount(accountUpdaterJsonDto, avatars, licenses, idCards));
    }

    /**
     * TODO Multipart transfer images avatars
     * @param dto
     * @return
     * @throws AccountExistException
     * @throws AccountNotFoundException
     */
    @PostMapping(value = V4_CREATE_ACCOUNT, consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UUID> addNewAccount(
            @RequestPart("account") String dto,
            @RequestPart(value = "avatar", required = false)  MultipartFile avatars,
            @RequestPart(value = "license", required = false) MultipartFile licenses,
            @RequestPart(value = "idCard", required = false) MultipartFile idCards)
            throws AccountExistException, AccountNotFoundException, JsonProcessingException, FirebaseAuthException {
        AccountCreatorReqDto accountCreatorReqDto = new ObjectMapper().readValue(dto, AccountCreatorReqDto.class);
        UUID account = accountService.createAccount(accountCreatorReqDto, avatars, licenses, idCards);
        return ok(account);
    }

}
