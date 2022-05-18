package com.springframework.csscapstone.utils.exception_utils.customer_exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CustomerExistedException extends RuntimeException {
    public CustomerExistedException(String message) {
        super(message);
    }
}
