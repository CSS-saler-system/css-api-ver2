package com.springframework.csscapstone.config.security.services;

import com.springframework.csscapstone.utils.security_provider_utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@PropertySource(value = "classpath:application-securities.properties")
public class AuthenticationHandlerFilter extends OncePerRequestFilter {
    private final TokenProvider jwtTokenProvider;
    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationHandlerFilter.class);

    @Value("${jwt.token.authorization}")
    private String authorityJwt;

    @Value("${jwt.token.bearer-header}")
    private String tokenPrefix;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(authorityJwt);
        if (StringUtils.isEmpty(authorizationHeader) || !authorizationHeader.startsWith(tokenPrefix)){
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorizationHeader.substring(tokenPrefix.length());
        String subject = jwtTokenProvider.getSubject(token);

        if (jwtTokenProvider.isTokenValid(subject , token)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
            Authentication authentication = jwtTokenProvider.getAuthentication(subject, authorities, request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }
}
