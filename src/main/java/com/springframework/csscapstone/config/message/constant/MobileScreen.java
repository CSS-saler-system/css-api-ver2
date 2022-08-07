package com.springframework.csscapstone.config.message.constant;

public enum MobileScreen {
    SCREEN("screen"),
    INFO("id"),
    PRODUCT_DETAIL("/product_detail"),
    ORDER_DETAIL("/order_detail"),
    CAMPAIGN("/campaign_detail"),
    REQUEST("/request_screen");

    private final String screen;

    MobileScreen(String screen) {
        this.screen = screen;
    }

    public String getScreen() {
        return screen;
    }
}
