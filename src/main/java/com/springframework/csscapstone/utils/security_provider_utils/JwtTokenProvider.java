package com.springframework.csscapstone.utils.security_provider_utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.springframework.csscapstone.data.domain.Account;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@PropertySource(value = "classpath:application-securities.properties")
public class JwtTokenProvider {

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

    /**
     * TODO old versions login in this app
     * @param principal
     * @return
     */
    public String generateJwtToken(UserDetails principal) {
        String role = getClaimsFromUser(principal);

        return JWT.create()
                .withIssuer(issuer)
                .withAudience(audience)
                .withSubject(principal.getUsername())
                .withClaim(authorityHeader, role)
                .withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(expiredTime)))
                .sign(Algorithm.HMAC512(this.secretString.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * TODO Generate token by using email For Service
     * @param role
     * @param email
     * @return
     */
    public String generateJwtTokenForWeb(String role, String email) {
        return JWT.create()
                .withIssuer(issuer)
                .withAudience(audience)
                .withSubject(email)
                .withClaim(authorityHeader, role)
                .withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(expiredTime)))
                .sign(Algorithm.HMAC512(this.secretString.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * TODO Generate token by using phone for Service
     * @param role
     * @param phone
     * @return
     */
    public String generateJwtTokenForCollaborator(String role, String phone) {
        return JWT.create()
                .withIssuer(issuer)
                .withAudience(audience)
                .withSubject(phone)
                .withClaim(authorityHeader, role)
                .withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(expiredTime)))
                .sign(Algorithm.HMAC512(this.secretString.getBytes(StandardCharsets.UTF_8)));
    }

    private String getClaimsFromUser(UserDetails principal) {
        return principal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    public String getSubject(String token) {
        JWTVerifier verifier = getVerifier();
        return verifier.verify(token).getSubject();
    }



    public boolean isTokenValid(String email, String token) {
        JWTVerifier verifier = getVerifier();
        return StringUtils.isNotEmpty(email)
                || !verifier.verify(token).getExpiresAt().before(new Date());
    }



    public List<GrantedAuthority> getAuthorities(String token) {
        JWTVerifier verifier = getVerifier();

        return Stream.of(verifier.verify(token).getClaim(authorityHeader))
                .map(Claim::asString)
                .map(AuthorityUtils::commaSeparatedStringToAuthorityList)
                .findFirst().get();
    }

    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null, authorities);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }

    private JWTVerifier getVerifier() {
        Algorithm algorithm = Algorithm.HMAC512(this.secretString.getBytes(StandardCharsets.UTF_8));
        return JWT.require(algorithm)
                .withIssuer(issuer)
                .withAudience(audience)
                .build();
    }
}
