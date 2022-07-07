package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.data.domain.User;
import com.springframework.csscapstone.services.EmailServices;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServicesImpl implements EmailServices {
//    private final JavaMailSender javaMailSender;

    @Override
    @SneakyThrows
    public void sendEmailNotification(User user) {
//        SimpleMailMessage mail = new SimpleMailMessage();
//
//        mail.setTo(user.getEmailAddress());
//        mail.setFrom("test@gmail.com");
//        mail.setSubject("This is subject");
//        mail.setText("""
//                    Hi, my Friends, %s
//                """.formatted(user.getName()));
//
//        javaMailSender.send(mail);
    }
}
