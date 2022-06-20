package com.springframework.csscapstone.config.security;

import com.springframework.csscapstone.config.security.services.JwtAccessDeniedHandler;
import com.springframework.csscapstone.config.security.services.JwtAuthenticationEntryPoint;
import com.springframework.csscapstone.config.security.services.AuthenticationHandlerFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final AuthenticationHandlerFilter authenticationHandlerFilter;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    public static final String[] PUBLIC_URLS = {
            "/", "/user/**", "/user/image/**", "/blob/**",
            "/api/v1.0/admin/login/**",
            "/api/v2.0/enterprise/login/**",
            "/api/v3.0/collaborator/login/**",
    };

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/swagger-ui/**", "/configuration/**", "/swagger-resources/**", "/v3/api-docs/**", "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> corsConfiguration())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilterBefore(authenticationHandlerFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(
                        request -> request
                                .antMatchers(PUBLIC_URLS).permitAll()
                                .antMatchers(ADMIN + "/**").hasAuthority("Admin")
                                .antMatchers(ENTERPRISE + "/**").hasAuthority("Enterprise")
                                .antMatchers(COLLABORATOR + "/**").hasAuthority("Collaborator")
                                .antMatchers(MODERATOR + "/**").hasAuthority("Moderator")
                                .anyRequest().authenticated())
                .logout(_logout -> _logout.logoutUrl(USER_LOGOUT))
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(jwtAccessDeniedHandler)
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint));
    }

    //    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .cors()
//                .configurationSource(request -> corsConfiguration()).and()
////                .cors().and()
//                .sessionManagement().sessionCreationPolicy(STATELESS).and()
//
//                .authorizeRequests().antMatchers(PUBLIC_URLS).permitAll()
//
//                .antMatchers(ADMIN + "/**").hasAuthority("Admin")
//                .antMatchers(ENTERPRISE + "/**").hasAuthority("Enterprise")
//                .antMatchers(COLLABORATOR + "/**").hasAuthority("Collaborator")
//                .antMatchers(MODERATOR + "/**").hasAuthority("Moderator")
//                .anyRequest().authenticated()
//
//                .and()
//                .exceptionHandling()
//                .accessDeniedHandler(jwtAccessDeniedHandler)
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//
//                .and().addFilterBefore(authenticationHandlerFilter, UsernamePasswordAuthenticationFilter.class);
//
//        //after logout success, invalidate session
//        http.logout().logoutUrl(USER_LOGOUT).invalidateHttpSession(true);
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfiguration corsConfiguration() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

//        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));

        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));

        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));

        corsConfiguration.setAllowCredentials(true);

        corsConfiguration.setExposedHeaders(Collections.singletonList("Authorization"));

        return corsConfiguration;
    }


}
