package com.springframework.csscapstone.config.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class AppUserDetail implements UserDetails, Serializable {
    private final Account entity;
    private final String token;

    @JsonProperty("id")
    public UUID getAccountId() {return entity.getId();}

    @JsonProperty("role")
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(entity.getRole())
                        .map(Role::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
    }


    @JsonIgnore
    @Override
    public String getPassword() {
        return entity.getPassword();
    }

    @JsonProperty("phone")
    @Override
    public String getUsername() {
        return entity.getPhone();
    }

    @JsonProperty("jwt_token")
    public String getToken() {return this.token; }

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
