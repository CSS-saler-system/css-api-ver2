package com.springframework.csscapstone.controller.enterprise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.auth.FirebaseAuthException;
import com.springframework.csscapstone.payload.request_dto.enterprise.EnterpriseSignUpDto;
import com.springframework.csscapstone.services.AccountService;
import com.springframework.csscapstone.services.LoginService;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;
import com.springframework.csscapstone.utils.security_provider_utils.TokenProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
import java.util.function.Supplier;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.*;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Login - (Enterprise)")
@PropertySource(value = "classpath:application-securities.properties")
@RestController
@RequiredArgsConstructor
public class EnterpriseLoginController {
    private final TokenProvider jwtTokenProvider;
    private final LoginService loginService;
    private final AccountService accountService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${jwt.token.header}")
    private String tokenHeader;

    @PostMapping(ENTERPRISE_LOGIN)
    public ResponseEntity<?> openLogin(
            @RequestParam(name = "loginToken") String firebaseToken,
            @RequestParam(name = "registrationToken", required = false, defaultValue = "") String registrationToken) throws FirebaseAuthException {
        UserDetails userDetails = this.loginService.enterpriseLoginByFirebaseService(firebaseToken, registrationToken);
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    @PutMapping(
            value = ENTERPRISE_SIGNUP,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> singupEnterprise(
            @RequestPart("enterpriseDto") String enterpriseString,
            @RequestPart("avatar") MultipartFile avatar,
            @RequestPart("businessLicences") MultipartFile licences) throws JsonProcessingException {
        EnterpriseSignUpDto enterprise = new ObjectMapper().readValue(enterpriseString, EnterpriseSignUpDto.class);
        UUID result = this.accountService
                .singUpEnterprise(enterprise, avatar, licences)
                .orElseThrow(somethingWrongException);
        return ok(result);
    }

    @GetMapping(ENTERPRISE_GET_SIGNUP + "/{accountId}")
    public ResponseEntity<?> getAccountById(@PathVariable("accountId") UUID id)
            throws AccountInvalidException {
        return ok(accountService.getByIdSignup(id));
    }

    private final Supplier<RuntimeException> somethingWrongException = () -> new RuntimeException("Something went wrong during sign up account");

}
