package com.springframework.csscapstone.config.firebase_config.model;

public enum NotificationParameter {
    SOUND("default"),
    COLOR("#FFFF00");
    private String value;

    NotificationParameter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
