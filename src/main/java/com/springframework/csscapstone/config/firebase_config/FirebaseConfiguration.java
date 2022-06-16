package com.springframework.csscapstone.config.firebase_config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Configuration
@RequiredArgsConstructor
public class FirebaseConfiguration {
    private final Logger LOGGER = LoggerFactory.getLogger(FirebaseConfiguration.class);

    @Value("${firebase.file.configuration}") //C:\Users\ductlm\Downloads\firebase_config.json
    private String configurationFile;

    @PostConstruct
    public void setupFirebase() throws IOException {
        InputStream inputStream = Files.newInputStream(Paths.get(configurationFile));

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(inputStream))
                .build();
        FirebaseApp.initializeApp(options);
    }

    @Bean
    public FirebaseAuth createFirebaseAuth() {
        return FirebaseAuth.getInstance(FirebaseApp.getInstance());
    }

}
