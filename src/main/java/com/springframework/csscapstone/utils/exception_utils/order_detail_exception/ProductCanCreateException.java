package com.springframework.csscapstone.utils.exception_utils.order_detail_exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductCanCreateException extends RuntimeException {
    public ProductCanCreateException(String message) {
        super(message);
    }
}
