package com.springframework.csscapstone.config.security.model;

import lombok.Data;

@Data
public class UserLogin {
    private final String username;
    private final String password;
}
