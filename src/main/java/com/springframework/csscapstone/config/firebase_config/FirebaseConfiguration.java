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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
@RequiredArgsConstructor
public class FirebaseConfiguration {
    private final Logger LOGGER = LoggerFactory.getLogger(FirebaseConfiguration.class);

    @Value("${firebase.file.configuration}")
    private String configurationFile;

    @PostConstruct
    public void setupFirebase() throws IOException {
//        String configurationFile = "firebase_config.json";
        InputStream inputStream = new FileInputStream(configurationFile);

//        ResourceLoader resourceLoader = new DefaultResourceLoader();
//        InputStream inputStream = resourceLoader.getResource("firebase_config.json").getInputStream();

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
