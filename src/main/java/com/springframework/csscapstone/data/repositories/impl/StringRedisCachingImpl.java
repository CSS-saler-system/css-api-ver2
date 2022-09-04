package com.springframework.csscapstone.data.repositories.impl;

import com.springframework.csscapstone.data.repositories.StringRedisCaching;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
@RequiredArgsConstructor
public class StringRedisCachingImpl implements StringRedisCaching {
    private final RedisTemplate<String, String> redisTemplate;
    private HashOperations<String, String, String> stringCacheable;
    private static final String ENTITY_STRING = "ENTITY_STRING";

    @PostConstruct
    private void init() {
        this.stringCacheable = redisTemplate.opsForHash();
    }

    @Override
    public String getByKey(String key) {
        return this.stringCacheable.get(ENTITY_STRING, key);
    }

    @Override
    public void deleteByKey(String key) {
        this.stringCacheable.delete(ENTITY_STRING, key);
    }

    @Override
    public void addOrganization(String key, String value) {
        this.stringCacheable.put(ENTITY_STRING, key, value);
    }

    @Override
    public void updateOrganization(String key, String value) {
        this.stringCacheable.put(ENTITY_STRING, key, value);

    }
}
