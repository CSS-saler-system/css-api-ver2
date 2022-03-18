package com.springframework.csscapstone.utils.exception_utils.account_exception;

public class AccountExistException extends RuntimeException {
    public AccountExistException(String message) {
        super(message);
    }
}
