package com.springframework.csscapstone.payload.system_model;

import lombok.Data;

import java.io.Serializable;

@Data
public class NotificationDto implements Serializable {
    private final String title;
    private final String message;
    private final String pathImage;
//    private final String topic;
}
