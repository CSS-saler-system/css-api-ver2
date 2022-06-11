package com.springframework.csscapstone.config.security.services.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class WebUserDetail implements UserDetails {
    private final Account entity;
    private final String jwt;
    @JsonProperty("id")
    public UUID getId() {return this.entity.getId();}
    @JsonProperty("role")
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(entity.getRole())
                        .map(Role::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
    }
//    @JsonProperty("password")
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

    @JsonProperty("jwt_token")
    public String getJwtToken() {return this.jwt; }

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
