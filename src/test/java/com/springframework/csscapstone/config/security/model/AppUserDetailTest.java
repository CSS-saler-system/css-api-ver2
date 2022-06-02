package com.springframework.csscapstone.config.security.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Role;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.utils.security_provider_utils.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.context.ActiveProfiles;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@ActiveProfiles(value = "test")
@AutoConfigureTestDatabase(replace = NONE)
@ComponentScan(basePackages = {"com.springframework.csscapstone.utils.security_provider_utils"})
class AppUserDetailTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Test
    void genJsonFromAppUserDetailsTests() throws JsonProcessingException {
        String this_is_token = new ObjectMapper()
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(
                        new AppUserDetail(new Account()
                                .setPhone("0345184306")
                                .setRole(new Role("Collaborator")),
                                "This is token"));
        System.out.println(this_is_token);
    }

    @Test
    void accountOnDatabaseTest() {
        Optional<Account> accountByEmail =
                accountRepository.findAccountByEmail("la@gmail.com");

        accountByEmail.ifPresent(acc -> {
            try {
                System.out.println(
                        new ObjectMapper().writerWithDefaultPrettyPrinter()
                                .writeValueAsString(new AppUserDetail(acc, UUID.randomUUID().toString()))
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void accountOnDatabaseWithTokenProviderTest() {
        Optional<Account> accountByEmail =
                accountRepository.findAccountByEmail("ben@gmail.com");

        accountByEmail.ifPresent(acc -> {
            try {
                System.out.println(
                        new ObjectMapper().writerWithDefaultPrettyPrinter()
                                .writeValueAsString(new AppUserDetail(acc,
                                        this.tokenProvider
                                                .generateJwtTokenForCollaborator(acc.getRole().getName(),
                                                        acc.getPhone())))
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void getSubFromAccessTokenTest() {
        String subject = this.tokenProvider.getSubject("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJBdXRob3JpemF0aW9uIjoiRW50ZXJwcmlzZSIsImF1ZCI6IkR1Y1RsbXNlMTMwNDMyIiwic3ViIjoiMDEyMzQ1MzUzMjQ1IiwiaXNzIjoiQ1NTLVNZU1RFTSIsImV4cCI6MTY1NDU2MjM5MH0.z5pMmkTS7LkXAyF0gEjMxSrmmSaadPSm8wWC7qoTbhGAgtbYGzs0ts4KyhWMunQSzMO_zc71mcq37H04QZdwyA");
        System.out.println(subject);
    }

    @Test
    void checkValidFromAccessTokenTest() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJBdXRob3JpemF0aW9uIjoiRW50ZXJwcmlzZSIsImF1ZCI6IkR1Y1RsbXNlMTMwNDMyIiwic3ViIjoiMDEyMzQ1MzUzMjQ1IiwiaXNzIjoiQ1NTLVNZU1RFTSIsImV4cCI6MTY1NDU2MjM5MH0.z5pMmkTS7LkXAyF0gEjMxSrmmSaadPSm8wWC7qoTbhGAgtbYGzs0ts4KyhWMunQSzMO_zc71mcq37H04QZdwyA";
        String subject = this.tokenProvider
                .getSubject(token);
        System.out.println(subject);
        assertTrue(this.tokenProvider.isTokenValid(subject, token));
        System.out.println(this.tokenProvider.isTokenValid(subject, token));
    }

    @Test
    void getRoleFromTokenTest() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJBdXRob3JpemF0aW9uIjoiRW50ZXJwcmlzZSIsImF1ZCI6IkR1Y1RsbXNlMTMwNDMyIiwic3ViIjoiMDEyMzQ1MzUzMjQ1IiwiaXNzIjoiQ1NTLVNZU1RFTSIsImV4cCI6MTY1NDU2MjM5MH0.z5pMmkTS7LkXAyF0gEjMxSrmmSaadPSm8wWC7qoTbhGAgtbYGzs0ts4KyhWMunQSzMO_zc71mcq37H04QZdwyA";
        List<GrantedAuthority> authorities =
                this.tokenProvider.getAuthorities(token);
        authorities.forEach(System.out::println);
    }

    @Test
    void getAuthenticationFromTokenTest() {

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServerName("www.example.com");

        request.setRequestURI("/foo");
        request.setQueryString("param1=value1&param");

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJBdXRob3JpemF0aW9uIjoiRW50ZXJwcmlzZSIsImF1ZCI6IkR1Y1RsbXNlMTMwNDMyIiwic3ViIjoiMDEyMzQ1MzUzMjQ1IiwiaXNzIjoiQ1NTLVNZU1RFTSIsImV4cCI6MTY1NDU2MjM5MH0.z5pMmkTS7LkXAyF0gEjMxSrmmSaadPSm8wWC7qoTbhGAgtbYGzs0ts4KyhWMunQSzMO_zc71mcq37H04QZdwyA";
        String subject = this.tokenProvider
                .getSubject(token);
        List<GrantedAuthority> authorities =
                this.tokenProvider.getAuthorities(token);
        Authentication authentication = this.tokenProvider.getAuthentication(subject, authorities, request);
        assertNotNull(authentication);
        System.out.println(authentication);
    }
}