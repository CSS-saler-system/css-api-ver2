package com.springframework.csscapstone.services.business;

import com.google.firebase.auth.FirebaseAuthException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountLoginWithEmailException;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoginService {

    UserDetails loginByFirebaseService(String firebaseToken) throws FirebaseAuthException, AccountLoginWithEmailException;
}
