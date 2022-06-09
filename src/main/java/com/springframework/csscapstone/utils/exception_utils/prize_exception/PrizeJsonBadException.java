package com.springframework.csscapstone.utils.exception_utils.prize_exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class PrizeJsonBadException extends RuntimeException {
    public PrizeJsonBadException(String message) {
        super(message);
    }
}
