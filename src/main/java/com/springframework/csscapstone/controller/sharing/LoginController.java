package com.springframework.csscapstone.controller.sharing;

import com.google.firebase.auth.FirebaseAuthException;
import com.springframework.csscapstone.config.security.model.UserLogin;
import com.springframework.csscapstone.services.AccountService;
import com.springframework.csscapstone.services.LoginService;
import com.springframework.csscapstone.payload.custom.creator_model.AccountRegisterDto;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountExistException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountLoginWithEmailException;
import com.springframework.csscapstone.utils.security_provider_utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@PropertySource(value = "classpath:application-securities.properties")
public class LoginController {
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountService services;
    private final LoginService loginService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${jwt.token.header}")
    private String tokenHeader;

    @PostMapping(USER_LOGIN)
    public ResponseEntity<UserDetails> login(@Valid @RequestBody UserLogin userLogin) {
        authentication(userLogin.getEmail(), userLogin.getPassword());
        UserDetails _principal = userDetailsService.loadUserByUsername(userLogin.getEmail());
        HttpHeaders jwtHeader = getHeader(_principal);
        LOGGER.info("The authentication {}", _principal);
        return new ResponseEntity<>(_principal, jwtHeader, OK);
    }

    @PostMapping(USER_OPEN_LOGIN)
    public ResponseEntity<?> openLogin(@RequestParam String firebaseToken)
            throws FirebaseAuthException, AccountLoginWithEmailException {
        UserDetails _principal = this.loginService.loginByFirebaseService(firebaseToken);
        HttpHeaders jwtHeader = getHeader(_principal);
        return new ResponseEntity<>(_principal, jwtHeader, OK);
    }

    @PostMapping(USER_REGISTER)
    public ResponseEntity<?> register(@Valid @RequestBody AccountRegisterDto dto) throws AccountExistException {
        UUID uuid = this.services.registerAccount(dto);
        UserDetails _principal = userDetailsService.loadUserByUsername(dto.getEmail());
        HttpHeaders jwtHeader = getHeader(_principal);
        return new ResponseEntity<>(_principal, jwtHeader, OK);
    }

    private void authentication(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

    private HttpHeaders getHeader(UserDetails principal) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(tokenHeader, jwtTokenProvider.generateJwtToken(principal));
        return httpHeaders;
    }

}
