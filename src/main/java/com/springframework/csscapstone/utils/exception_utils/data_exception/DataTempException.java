package com.springframework.csscapstone.utils.exception_utils.data_exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class DataTempException extends RuntimeException {
    public DataTempException(String message) {
        super(message);
    }
}
