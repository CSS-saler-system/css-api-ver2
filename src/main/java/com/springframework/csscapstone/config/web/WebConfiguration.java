package com.springframework.csscapstone.config.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@EnableCaching
public class WebConfiguration {

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

}
