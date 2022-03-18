package com.springframework.csscapstone.utils.exception_utils.category_exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoryInvalidException extends RuntimeException {
    public CategoryInvalidException(String message) {
        super(message);
    }
}
