package com.springframework.csscapstone.utils.exception_catch_utils;

import java.io.IOException;
import java.util.function.Function;

public class ExceptionCatchHandler {
    public static <T, R> Function<T, R> wrap(ExceptionUtils<T, R> fn) {
        return t -> {
            try {
                return fn.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        };

    }

    public static Runnable wrapVoid(ExceptionVoidUtils fn) {
        return () -> {
            try {
                fn.run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
