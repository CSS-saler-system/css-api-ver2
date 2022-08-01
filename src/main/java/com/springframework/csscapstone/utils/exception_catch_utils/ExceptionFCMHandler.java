package com.springframework.csscapstone.utils.exception_catch_utils;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class ExceptionFCMHandler {

    public static <T> Consumer<T> fcmException(FcmExceptionHandler<T> ex) {
        return t -> {
            try {
                ex.run(t);
            } catch (ExecutionException | JsonProcessingException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
    }

}
