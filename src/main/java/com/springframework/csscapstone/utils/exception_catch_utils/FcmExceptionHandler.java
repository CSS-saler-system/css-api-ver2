package com.springframework.csscapstone.utils.exception_catch_utils;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.concurrent.ExecutionException;

@FunctionalInterface
public interface FcmExceptionHandler<T> {

    void run(T t) throws ExecutionException,JsonProcessingException,InterruptedException;

}
