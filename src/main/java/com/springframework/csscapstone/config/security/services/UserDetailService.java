package com.springframework.csscapstone.config.security.services;

import com.springframework.csscapstone.config.security.services.model.AdministrationWebDetail;
import com.springframework.csscapstone.config.security.services.model.WebUserDetail;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.utils.security_provider_utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final JwtTokenProvider jwtTokenProvider;
    private final AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        LOGGER.info("The email: {}", email);
        return repository.findAccountByEmail(email)
                .map(account -> new WebUserDetail(account,
                        jwtTokenProvider.generateJwtToken(new AdministrationWebDetail(account))))
                .orElseThrow(() -> new UsernameNotFoundException("The user: " + email + " cant be not found"));
    }


}
