package com.springframework.csscapstone.utils.exception_utils.campaign_exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CampaignInvalidException extends RuntimeException {
    public CampaignInvalidException(String message) {
        super(message);
    }
}
