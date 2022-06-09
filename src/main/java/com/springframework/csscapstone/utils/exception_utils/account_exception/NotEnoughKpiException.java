package com.springframework.csscapstone.utils.exception_utils.account_exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class NotEnoughKpiException extends RuntimeException {
    public NotEnoughKpiException(String message) {
        super(message);
    }
}
