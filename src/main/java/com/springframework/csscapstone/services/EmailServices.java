package com.springframework.csscapstone.services;

import com.springframework.csscapstone.data.domain.User;

public interface EmailServices {
    public void sendEmailNotification(User user);
}
