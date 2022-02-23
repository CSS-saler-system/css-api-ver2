package com.springframework.csscapstone.css_business.services;

import com.google.firebase.auth.FirebaseAuthException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountLoginWithEmailException;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoginService {

    UserDetails loginByFirebaseService(String firebaseToken) throws FirebaseAuthException, AccountLoginWithEmailException;
}
