package com.springframework.csscapstone.utils.exception_utils.prize_exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class PrizeNotFoundException extends RuntimeException {
    public PrizeNotFoundException(String message) {
        super(message);
    }
}
