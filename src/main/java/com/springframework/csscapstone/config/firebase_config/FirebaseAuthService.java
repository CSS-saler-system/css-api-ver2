package com.springframework.csscapstone.config.firebase_config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

import static com.springframework.csscapstone.utils.exception_catch_utils.ExceptionCatchHandler.wrapFirebaseVoid;
import static com.springframework.csscapstone.utils.exception_catch_utils.ExceptionCatchHandler.wrapVoid;

@Component
@RequiredArgsConstructor
public class FirebaseAuthService {

    private final AccountRepository accountRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Async("threadPoolTaskExecutor")
    public void saveAccountOnFirebase(String email, String phone) {
        //check email or phone is null:
        if (StringUtils.isNotEmpty(email) && StringUtils.isNotEmpty(phone)) {
            UserRecord.CreateRequest createRequest = new UserRecord.CreateRequest()
                    .setEmail(email)
                    .setPhoneNumber(phone)
                    .setEmailVerified(true);
            LOGGER.info("The firebase running on Thread: " + Thread.currentThread().getName());
            FirebaseAuth.getInstance().createUserAsync(createRequest);
            return;
        }
        throw new RuntimeException("Phone or Email must not be null");
    }
}
