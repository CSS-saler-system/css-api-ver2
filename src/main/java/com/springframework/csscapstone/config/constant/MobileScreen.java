package com.springframework.csscapstone.config.constant;

public enum MobileScreen {

    CAMPAIGN("Campaign");

    private final String screen;

    MobileScreen(String screen) {
        this.screen = screen;
    }

    public String getScreen() {
        return screen;
    }
}
