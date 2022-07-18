package com.springframework.csscapstone.utils.exception_catch_utils;

import com.fasterxml.jackson.core.JsonProcessingException;

@FunctionalInterface
public interface JsonProcessingExceptionUtils<T> {
    void consume(T t) throws JsonProcessingException;
}
