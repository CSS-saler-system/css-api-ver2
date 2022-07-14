package com.springframework.csscapstone.utils.exception_catch_utils;

import com.fasterxml.jackson.core.JsonProcessingException;

@FunctionalInterface
public interface WrapJsonProcessingException<T,R> {
    R apply(T t) throws JsonProcessingException;
}
