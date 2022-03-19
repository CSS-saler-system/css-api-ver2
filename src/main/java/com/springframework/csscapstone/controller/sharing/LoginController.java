package com.springframework.csscapstone.controller.sharing;

import com.google.firebase.auth.FirebaseAuthException;
import com.springframework.csscapstone.config.security.model.UserLogin;
import com.springframework.csscapstone.services.business.AccountService;
import com.springframework.csscapstone.services.business.LoginService;
import com.springframework.csscapstone.services.model_dto.custom.creator_model.AccountRegisterDto;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountExistException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountLoginWithEmailException;
import com.springframework.csscapstone.utils.security_provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
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

    @Value("${jwt.token.header}")
    private String tokenHeader;

    @PostMapping(USER_LOGIN)
    public ResponseEntity<UserDetails> login(@RequestBody UserLogin userLogin) {
        authentication(userLogin.getUsername(), userLogin.getPassword());
        UserDetails _principal = userDetailsService.loadUserByUsername(userLogin.getUsername());
        HttpHeaders jwtHeader = getHeader(_principal);
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
        UserDetails _principal = userDetailsService.loadUserByUsername(dto.getUsername());
        HttpHeaders jwtHeader = getHeader(_principal);
        return new ResponseEntity<>(_principal, jwtHeader, OK);
    }

    private void authentication(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private HttpHeaders getHeader(UserDetails principal) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(tokenHeader, jwtTokenProvider.generateJwtToken(principal));
        return httpHeaders;
    }

}
