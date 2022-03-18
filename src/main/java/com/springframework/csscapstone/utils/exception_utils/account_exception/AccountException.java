package com.springframework.csscapstone.utils.exception_utils.account_exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountException extends RuntimeException {
    public AccountException(String message) {
        super(message);
    }
}
