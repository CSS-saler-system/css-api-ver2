package com.springframework.csscapstone.controller.moderator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuthException;
import com.springframework.csscapstone.config.message.constant.MessageConstant;
import com.springframework.csscapstone.config.security.services.model.UserLogin;
import com.springframework.csscapstone.payload.request_dto.admin.AccountCreatorReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResDto;
import com.springframework.csscapstone.payload.response_dto.moderator.AccountModeratorPageResDto;
import com.springframework.csscapstone.payload.sharing.AccountUpdaterJsonDto;
import com.springframework.csscapstone.services.AccountService;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountExistException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;
import java.util.UUID;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.ADMIN_LOGIN;
import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.Account.*;
import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.MODERATOR_LOGIN;
import static com.springframework.csscapstone.config.message.constant.RegexConstant.EMAIL_REGEX;
import static com.springframework.csscapstone.config.message.constant.RegexConstant.PHONE_REGEX;
import static org.springframework.http.HttpStatus.OK;
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

    /**
     * todo Get Account by Id
     *
     * @return
     */
    @GetMapping(V4_ACCOUNT_LIST)
    public ResponseEntity<?> getAllEnterprise(
            @RequestParam(value = "isActive", required = false) Boolean isActive,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize
    ) {
        PageImplResDto<AccountModeratorPageResDto> res = this.accountService.pageEnterprise(isActive, pageNumber, pageSize);
        return ok(res);
    }

    @PutMapping(V4_ACTIVE_ACCOUNT + "/{enterpriseId}")
    public ResponseEntity<?> activeAccount(@PathVariable("enterpriseId") UUID enterpriseId) {
        return ok(this.accountService.activeEnterprise(enterpriseId)
                ? MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS)
                : MessagesUtils.getMessage(MessageConstant.REQUEST_FAILURE));
    }
    @PutMapping(V4_DISABLE_ACCOUNT + "/{enterpriseId}")
    public ResponseEntity<?> disableAccount(@PathVariable("enterpriseId") UUID enterpriseId) {
        return ok(this.accountService.disableEnterprise(enterpriseId)
                ? MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS)
                : MessagesUtils.getMessage(MessageConstant.REQUEST_FAILURE));
    }



}
