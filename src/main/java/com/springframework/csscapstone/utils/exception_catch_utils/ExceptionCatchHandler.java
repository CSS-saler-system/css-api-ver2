package com.springframework.csscapstone.utils.exception_catch_utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

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

    public static UserRecord wrapFirebaseVoid(ExceptionVoidFirebaseUtils fn) {
        try {
            return fn.run();
        } catch (FirebaseAuthException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static <T, R> Function<T, R> wrapToJson(WrapJsonProcessingException<T, R> fn) {
        return t -> {
            try {
                return fn.apply(t);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }
    public static <T> UnaryOperator<T> peek(Consumer<T> consumer) {
        return x -> {
            consumer.accept(x);
            return x;
        };
    }

    public static <T> Consumer<T> completeSchedule(JsonProcessingExceptionUtils<T> utils) {
        return x -> {
            try {
                utils.consume(x);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
