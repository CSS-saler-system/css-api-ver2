package com.springframework.csscapstone.utils.exception_utils.campaign_exception;

import com.sun.xml.bind.v2.model.runtime.RuntimeReferencePropertyInfo;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class CampaignNotFoundException extends RuntimeException {
    public CampaignNotFoundException(String message) {
        super(message);
    }
}
