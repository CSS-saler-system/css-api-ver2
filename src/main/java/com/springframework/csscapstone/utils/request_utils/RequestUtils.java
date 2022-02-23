package com.springframework.csscapstone.utils.request_utils;

import java.util.Objects;

public class RequestUtils {
    public static String getRequestParam(String param) {
        return "%" + (Objects.nonNull(param) ? param : "") + "%";
    }
}
