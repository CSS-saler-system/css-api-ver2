package com.springframework.csscapstone.utils.security_provider_utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TokenProvider {
    String generateJwtToken(UserDetails principal);

    String getSubject(String token);

    boolean isTokenValid(String email, String token);

    List<GrantedAuthority> getAuthorities(String token);

    Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request);
}
