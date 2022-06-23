package com.springframework.csscapstone.functionalinterface;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.function.Function;

public class CheckedExceptionHandler {
    public static <T,R> Function<T,R> wrap(CheckedException<T,R> fn) {
        return t -> {
            try {
                return fn.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static <E, S> Function<E, S> json(ExceptionHandler<E, S> fn) {
        return e -> {
            try {
                return fn.jsonExceptionHandler(e);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}

