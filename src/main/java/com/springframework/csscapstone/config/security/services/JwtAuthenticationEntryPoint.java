package com.springframework.csscapstone.config.security.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.config.security.model.model_exception.HttpResponse;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException {

            HttpResponse httpResponse = new HttpResponse(
                    FORBIDDEN.value(),
                    FORBIDDEN,
                    FORBIDDEN.getReasonPhrase().toUpperCase(),
                    MessagesUtils.getMessage(MessageConstant.Auth.FORBIDDEN_MESSAGE));

            response.setContentType(APPLICATION_JSON_VALUE);
            response.setStatus(FORBIDDEN.value());

            OutputStream outputStream = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(outputStream, httpResponse);
            outputStream.flush();
    }
}
