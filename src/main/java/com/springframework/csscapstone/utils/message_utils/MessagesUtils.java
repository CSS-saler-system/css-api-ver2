package com.springframework.csscapstone.utils.message_utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessagesUtils {
    private static MessageSource messageSource;

    public MessagesUtils(MessageSource _messageSource) {
        messageSource = _messageSource;
    }

    public static String getMessage(String messageKey, Object... args) {
        if (messageSource == null)
            return "Something went wrong :((";
        return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
    }

}
