package com.springframework.csscapstone.config.security.model;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Role;
import lombok.RequiredArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@PropertySource(value = "classpath:application-securities.properties")
@RequiredArgsConstructor
public class WebUserDetail implements UserDetails, Serializable {
    private final Account entity;

    @Value("${security.secret-string}")
    private String secretString;

    @Value("${jwt.token.authorization}")
    private String authorityHeader;

    @Value("${jwt.token.issuer}")
    private String issuer;

    @Value("${jwt.token.audience}")
    private String audience;

    @Value("${jwt.token.exp-time}")
    private String expiredTime;

    @JsonProperty("role")
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(entity.getRole())
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @JsonProperty("token")
    public String getToken() {
        return JWT.create()
                .withIssuer(issuer)
                .withAudience(audience)
                .withSubject(entity.getEmail())
                .withClaim(authorityHeader, entity.getRole().getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(expiredTime)))
                .sign(Algorithm.HMAC512(this.secretString.getBytes(StandardCharsets.UTF_8)));
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return entity.getPassword();
    }

    @JsonProperty("email")
    @Override
    public String getUsername() {
        return entity.getEmail();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return entity.getNonLock();
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return entity.getIsActive();
    }
}
