package com.springframework.csscapstone.controller.moderator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.config.security.services.model.UserLogin;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.payload.request_dto.moderator.AccountModeratorUpdateReqDto;
import com.springframework.csscapstone.services.AccountService;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.UUID;
import java.util.function.Supplier;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Login (Moderator)")
public class ModeratorLoginController {

    private static final String MODERATOR_ROLE = "Moderator";
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;
    private final Supplier<RuntimeException> somethingWrongException = () -> new RuntimeException("Something went wrong during update account");

    @PostMapping(MODERATOR_LOGIN)
    public ResponseEntity<UserDetails> login(
            @RequestBody @Valid UserLogin userLogin
    ) {
        authentication(userLogin.getEmail(), userLogin.getPassword());
        UserDetails _principal = userDetailsService.loadUserByUsername(userLogin.getEmail());
        _principal.getAuthorities().stream()
                .filter(authorize -> authorize.getAuthority().equals(MODERATOR_ROLE))
                .findFirst()
                .orElseThrow(() -> new BadCredentialsException("Wrong username or password!!!"));
        return new ResponseEntity<>(_principal, OK);
    }


    @PutMapping(
            value = MODERATOR_UPDATE + "/{moderatorId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateModerator(
            @PathVariable("moderatorId") UUID moderatorId,
            @RequestPart("enterpriseDto") String moderatorString,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar) throws JsonProcessingException {
        AccountModeratorUpdateReqDto moderator = new ObjectMapper().readValue(moderatorString, AccountModeratorUpdateReqDto.class);
        Account res = this.accountService
                .updateAccountForModerator(moderatorId, moderator, avatar)
                .orElseThrow(somethingWrongException);
        return ok(res.getId());
    }

    @GetMapping(MODERATOR_GET_SIGNUP + "/{accountId}")
    public ResponseEntity<?> getAccountById(@PathVariable("accountId") UUID id)
            throws AccountInvalidException {
        return ok(accountService.getByIdSignup(id));
    }

    private void authentication(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
