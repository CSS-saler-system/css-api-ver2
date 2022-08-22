package com.springframework.csscapstone.controller.sharing;

import com.springframework.csscapstone.config.security.services.model.UserLogin;
import com.springframework.csscapstone.utils.security_provider_utils.TokenProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.ADMIN_LOGIN;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@PropertySource(value = "classpath:application-securities.properties")
@Tag(name = "Login (Admin)")
public class AdminLoginController {
    private static final String ADMIN_ROLE = "Admin";
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider jwtTokenProvider;
//    private final LoginService loginService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Value("${jwt.token.header}")
    private String tokenHeader;

    @PostMapping(ADMIN_LOGIN)
    public ResponseEntity<UserDetails> login(@RequestBody @Valid UserLogin userLogin) {

        authentication(userLogin.getEmail(), userLogin.getPassword());
        UserDetails _principal = userDetailsService.loadUserByUsername(userLogin.getEmail());

        _principal.getAuthorities().stream()
                .filter(authorize -> authorize.getAuthority().equals(ADMIN_ROLE))
                .findFirst()
                .orElseThrow(() -> new BadCredentialsException("Wrong username or password!!!"));

        return new ResponseEntity<>(_principal, OK);
    }

    private void authentication(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

}
