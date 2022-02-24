package com.springframework.csscapstone.css_business.services.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.config.security.model.UserDetail;
import com.springframework.csscapstone.css_business.services.LoginService;
import com.springframework.csscapstone.css_data.repositories.AccountRepository;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountLoginWithEmailException;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final FirebaseAuth firebaseAuth;
    private final AccountRepository accountRepository;

    //todo This admit out useDetails
    @Override
    public UserDetails loginByFirebaseService(String firebaseToken) throws FirebaseAuthException, AccountLoginWithEmailException {
        FirebaseToken verifiedToken = firebaseAuth.verifyIdToken(firebaseToken);
        UserRecord _user = FirebaseAuth.getInstance().getUser(verifiedToken.getUid());
        String email = _user.getEmail();

        return accountRepository.findAccountByEmail(email)
                .map(UserDetail::new)
                .orElseThrow(this::getAccountLoginWithEmailException);
    }

    private AccountLoginWithEmailException getAccountLoginWithEmailException() {
        return new AccountLoginWithEmailException(MessagesUtils.getMessage(MessageConstant.Account.FAIL_LOGIN_EMAIL));
    }
}
