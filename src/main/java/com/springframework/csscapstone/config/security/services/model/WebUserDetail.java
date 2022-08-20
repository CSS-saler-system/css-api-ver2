package com.springframework.csscapstone.config.security.services.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.AccountImage;
import com.springframework.csscapstone.data.domain.Role;
import com.springframework.csscapstone.data.status.AccountImageType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.threeten.bp.LocalDate;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class WebUserDetail implements UserDetails {
    private final Account entity;
    private final String jwt;

    @JsonProperty("id")
    public UUID getId() {
        return this.entity.getId();
    }

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

    @JsonProperty("email")
    @Override
    public String getUsername() {
        return entity.getEmail();
    }

    @JsonProperty("name")
    public String name;

//    @JsonProperty("dob")
//    @JsonSerialize(using = LocalDateSerializer.class)
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    public LocalDate dob;

    @JsonProperty("phone")
    public String phone;

    @JsonProperty("description")
    public String description;

    @JsonProperty("address")
    public String address;

    @JsonProperty("jwt_token")
    public String getJwtToken() {
        return this.jwt;
    }

    //    @JsonIgnore
    @JsonProperty("isActive")
    @Override
    public boolean isEnabled() {
        return entity.getIsActive();
    }

    @JsonProperty("avatar")
    public String getImage() {
        return entity.getImages().
                stream()
                .filter(image -> image.getType().equals(AccountImageType.AVATAR))
                .map(AccountImage::getPath)
                .findFirst().orElse("");
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
}
