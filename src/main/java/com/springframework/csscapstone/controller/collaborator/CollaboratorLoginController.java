package com.springframework.csscapstone.controller.collaborator;

import com.google.firebase.auth.FirebaseAuthException;
import com.springframework.csscapstone.services.LoginService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.COLLABORATOR_LOGIN;
import static org.springframework.http.ResponseEntity.ok;

@Tag(name = "Login - (Collaborator)")
@RestController
@RequiredArgsConstructor
@PropertySource(value = "classpath:application-securities.properties")
public class CollaboratorLoginController {
    private final TokenProvider jwtTokenProvider;
    private final LoginService loginService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${jwt.token.header}")
    private String tokenHeader;

    @PostMapping(COLLABORATOR_LOGIN)
    public ResponseEntity<?> openLogin(@RequestParam String firebaseToken) throws FirebaseAuthException {
        UserDetails userDetails = this.loginService.collaboratorLoginByFirebaseService(firebaseToken);
//        HttpHeaders header = getHeader(userDetails);
//        return new ResponseEntity<>(userDetails, header, HttpStatus.OK);
        return ok(userDetails);
    }
//
//    private HttpHeaders getHeader(UserDetails principal) {
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(tokenHeader, jwtTokenProvider.generateJwtToken(principal));
//        return httpHeaders;
//    }
}
