package com.springframework.csscapstone.controller.enterprise;

import com.google.firebase.auth.FirebaseAuthException;
import com.springframework.csscapstone.payload.request_dto.enterprise.EnterpriseSignUpDto;
import com.springframework.csscapstone.services.AccountService;
import com.springframework.csscapstone.services.LoginService;
import com.springframework.csscapstone.utils.security_provider_utils.TokenProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
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

    @PostMapping(ENTERPRISE_SIGNUP)
    public ResponseEntity<?> singupEnterprise(@RequestBody EnterpriseSignUpDto enterprise) {
        Optional<UUID> uuid = this.accountService.singUpEnterprise(enterprise);
        UUID result = uuid.orElseThrow(somethingWrongException);
        return ok(result);
    }

    private final Supplier<RuntimeException> somethingWrongException = () -> new RuntimeException("Something went wrong during sign up account");

}
