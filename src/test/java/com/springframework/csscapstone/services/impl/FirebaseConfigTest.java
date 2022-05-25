package com.springframework.csscapstone.services.impl;

import com.google.firebase.auth.FirebaseAuth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(value =  "test")
class FirebaseConfigTest {
    @Autowired
    FirebaseAuth firebaseAuth;
    @Test
    void loginByFirebaseService() {
    }
}