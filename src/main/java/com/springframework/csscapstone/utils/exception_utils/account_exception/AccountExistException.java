package com.springframework.csscapstone.utils.exception_utils.account_exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class AccountExistException extends RuntimeException {
    public AccountExistException(String message) {
        super(message);
    }
}
