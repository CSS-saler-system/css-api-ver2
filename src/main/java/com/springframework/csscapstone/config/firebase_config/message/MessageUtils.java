package com.springframework.csscapstone.config.firebase_config.message;

import com.springframework.csscapstone.config.firebase_config.model.PushNotificationRequest;
import com.springframework.csscapstone.config.message.constant.MobileScreen;
import com.springframework.csscapstone.data.domain.*;
import com.springframework.csscapstone.data.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.springframework.csscapstone.utils.exception_catch_utils.ExceptionFCMHandler.fcmException;

@Component
@RequiredArgsConstructor
public class MessageUtils {

    private final NotificationRepository notificationRepository;

    public PushNotificationRequest saveNotification(Campaign campaign, Prize prize, AccountToken token, long kpi) {
        PushNotificationRequest notificationDto = new PushNotificationRequest(
                "The Finished Campaign",
                "You receive the prize: " + prize.getName() + ",price: " + prize.getPrice() + " with kpi: " + kpi,
                "The award prize",
                token.getRegistrationToken(),
                campaign.getImage().get(0).getPath());

        Notification notification = new Notification(
                notificationDto.getTitle(),
                notificationDto.getMessage(),
                notificationDto.getPathImage(),
                notificationDto.getTopic());

        notificationRepository.save(notification);
        return notificationDto;
    }


//
//    private void sendNotificationEnterprise(Campaign campaign, Account enterprise, long quantity) {
//        this.accountTokenRepository
//                .getAccountTokenByAccountOptional(enterprise.getId())
//                .map(List::stream)
//                .orElseGet(Stream::empty)
//                .findFirst()
//                .map(token -> {
//                    PushNotificationRequest notificationDto = new PushNotificationRequest(
//                            "The Finished Campaign",
//                            "The Campaign name: " + campaign.getName() + ",with quantity sold: " + quantity,
//                            "The Finished Campaign",
//                            token.getRegistrationToken(),
//                            campaign.getImage().get(0).getPath());
//
//                    Notification notification = new Notification(
//                            notificationDto.getTitle(), notificationDto.getMessage(),
//                            notificationDto.getPathImage(), notificationDto.getTopic());
//
//                    this.notificationRepository.save(notification);
//
//                    return notificationDto;
//                })
//                .ifPresent(fcmException(notification -> firebaseMessageService.sendMessage(
//                        Collections.singletonMap(MobileScreen.CAMPAIGN.getScreen(), campaign.getId().toString()),
//                        notification
//                )));
//
//    }

}
