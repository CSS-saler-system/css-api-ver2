package com.springframework.csscapstone.config.message.constant;

public enum MobileScreen {

    CAMPAIGN("Campaign"), REQUEST("RequestSelling");

    private final String screen;

    MobileScreen(String screen) {
        this.screen = screen;
    }

    public String getScreen() {
        return screen;
    }
}
