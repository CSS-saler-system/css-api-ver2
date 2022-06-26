package com.springframework.csscapstone.controller.collaborator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuthException;
import com.springframework.csscapstone.payload.sharing.AccountUpdaterJsonDto;
import com.springframework.csscapstone.services.AccountService;
import com.springframework.csscapstone.services.LoginService;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;
import com.springframework.csscapstone.utils.security_provider_utils.JwtTokenProvider;
import com.springframework.csscapstone.utils.security_provider_utils.TokenProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Account.V1_UPDATE_ACCOUNT;
import static com.springframework.csscapstone.config.constant.ApiEndPoint.Account.V3_UPDATE_ACCOUNT;
import static com.springframework.csscapstone.config.constant.ApiEndPoint.COLLABORATOR_LOGIN;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Login - (Collaborator)")
@RestController
@RequiredArgsConstructor
@PropertySource(value = "classpath:application-securities.properties")
public class CollaboratorLoginController {
    private final TokenProvider jwtTokenProvider;
    private final LoginService loginService;
    private final AccountService accountService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${jwt.token.header}")
    private String tokenHeader;

    @PostMapping(COLLABORATOR_LOGIN)
    public ResponseEntity<?> openLogin(
            @RequestParam(name = "loginToken") String firebaseToken,
            @RequestParam(name = "registrationToken", required = false, defaultValue = "") String registrationToken)
            throws FirebaseAuthException {

        UserDetails userDetails = this.loginService.collaboratorLoginByFirebaseService(firebaseToken, registrationToken);

        return ok(userDetails);
    }


    /**
     * todo update account by admin
     * @return
     * @throws AccountInvalidException
     */
    @PutMapping(value = V3_UPDATE_ACCOUNT, consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UUID> updateAccount(
            @RequestPart("account") String dto,
            @RequestPart(value = "avatar", required = false) MultipartFile avatars,
            @RequestPart(value = "license", required = false) MultipartFile licenses,
            @RequestPart(value = "idCard", required = false) MultipartFile idCards

    ) throws AccountInvalidException, JsonProcessingException {
        AccountUpdaterJsonDto accountUpdaterJsonDto = new ObjectMapper().readValue(dto, AccountUpdaterJsonDto.class);
        return ok(this.accountService.updateAccount(accountUpdaterJsonDto, avatars, licenses, idCards));
    }
}
