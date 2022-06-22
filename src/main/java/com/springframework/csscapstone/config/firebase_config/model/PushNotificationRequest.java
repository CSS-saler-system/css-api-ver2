package com.springframework.csscapstone.config.firebase_config.model;

import lombok.Data;

@Data
public class PushNotificationRequest {
    private final String title;
    private final String message;
    private final String topic;

    //token use for what registration token
    private final String token;
}
