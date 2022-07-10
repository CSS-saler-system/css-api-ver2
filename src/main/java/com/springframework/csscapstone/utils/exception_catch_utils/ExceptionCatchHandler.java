package com.springframework.csscapstone.utils.exception_catch_utils;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

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

    public static UserRecord wrapFirebaseVoid(ExceptionVoidFirebaseUtils fn) {
        try {
            return fn.run();
        } catch (FirebaseAuthException ex) {
            throw new RuntimeException(ex);
        }
    }

}
