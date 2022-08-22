package com.springframework.csscapstone.controller.moderator;

import com.springframework.csscapstone.config.security.services.model.UserLogin;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.MODERATOR_LOGIN;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@Tag(name = "Login (Moderator)")
public class ModeratorLoginController {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping(MODERATOR_LOGIN)
    public ResponseEntity<UserDetails> login(
            @RequestBody @Valid UserLogin userLogin
    ) {
        authentication(userLogin.getEmail(), userLogin.getPassword());
        UserDetails _principal = userDetailsService.loadUserByUsername(userLogin.getEmail());
        return new ResponseEntity<>(_principal, OK);
    }


    private void authentication(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
