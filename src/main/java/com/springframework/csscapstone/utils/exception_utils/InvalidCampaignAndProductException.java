package com.springframework.csscapstone.utils.exception_utils;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class InvalidCampaignAndProductException extends RuntimeException {
    public InvalidCampaignAndProductException(String message) {
        super(message);
    }
}
