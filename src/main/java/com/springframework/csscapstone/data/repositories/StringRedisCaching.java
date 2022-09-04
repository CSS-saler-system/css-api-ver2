package com.springframework.csscapstone.data.repositories;

public interface StringRedisCaching {
    String getByKey(String key);

    void deleteByKey(String key);

    void addOrganization(String key, String value);

    void updateOrganization(String key, String value);

}
