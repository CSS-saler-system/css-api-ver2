package com.springframework.csscapstone.utils.exception_catch_utils;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

public interface ExceptionVoidFirebaseUtils {
    UserRecord run() throws FirebaseAuthException;
}
