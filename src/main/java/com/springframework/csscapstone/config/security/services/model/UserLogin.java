package com.springframework.csscapstone.config.security.services.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserLogin {
    @NotEmpty(message = "The email must be not empty")
    @Email(message = "The format email was wrong")
    private final String email;
    @NotEmpty(message = "The password must be not empty")
    private final String password;

    private final String registrationToken;

    @JsonCreator
    public UserLogin(
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("registrationToken") String registrationToken) {
        this.email = email;
        this.password = password;
        this.registrationToken = registrationToken;
    }
}
