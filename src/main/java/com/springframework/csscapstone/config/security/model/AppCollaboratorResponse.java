package com.springframework.csscapstone.config.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Role;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class AppCollaboratorResponse implements Serializable, UserDetails {
    private final Account account;

    @JsonProperty("role")
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(account.getRole())
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @JsonProperty("password")
    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @JsonProperty("phone")
    @Override
    public String getUsername() {
        return account.getPhone();
    }

    @JsonProperty("")
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
