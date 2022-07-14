package com.springframework.csscapstone.utils.exception_catch_utils;

@FunctionalInterface
public interface ExceptionUtils<T, R> {
    R apply(T t) throws RuntimeException ;
}
