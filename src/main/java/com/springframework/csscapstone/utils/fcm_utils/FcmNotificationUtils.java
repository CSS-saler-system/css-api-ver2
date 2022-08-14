package com.springframework.csscapstone.utils.fcm_utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springframework.csscapstone.config.firebase_config.FirebaseMessageAsyncUtils;
import com.springframework.csscapstone.config.firebase_config.model.PushNotificationRequest;
import com.springframework.csscapstone.config.message.constant.MobileScreen;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Campaign;
import com.springframework.csscapstone.data.domain.Prize;
import com.springframework.csscapstone.data.repositories.AccountTokenRepository;
import com.springframework.csscapstone.data.status.CampaignStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static com.springframework.csscapstone.utils.exception_catch_utils.ExceptionFCMHandler.fcmException;

@Component
@RequiredArgsConstructor
public class FcmNotificationUtils {

    private final FirebaseMessageAsyncUtils firebaseMessageAsyncUtils;
    private final AccountTokenRepository accountTokenRepository;

    public void sendNotificationSentCampaign(Campaign campaign, CampaignStatus status, String token)
            throws ExecutionException, JsonProcessingException, InterruptedException {
        PushNotificationRequest notification = new PushNotificationRequest(
                "Campaign Approval Result",
                "The campaign was " + (status.equals(CampaignStatus.REJECTED) ? "reject" : "approval"),
                "The Campaign",
                token,
                campaign.getImage().get(0).getPath());

        Map<String, String> data = new HashMap<>();

        data.put(MobileScreen.CAMPAIGN.getScreen(), campaign.getId().toString());

        this.firebaseMessageAsyncUtils.sendMessage(data, notification);
    }

    public void sendNotificationFinishCampaign(Campaign campaign, Account account, Prize prize, Long kpi) {
        this.accountTokenRepository.getAccountTokenByAccountOptional(account.getId())
                .map(Collection::stream)
                .orElseGet(Stream::empty)
                .findFirst()
                .map(token -> new PushNotificationRequest(
                        "The Finished Campaign",
                        "You receive the prize: " + prize.getName() + ",price: " + prize.getPrice(),
                        "The award prize",
                        token.getRegistrationToken(),
                        campaign.getImage().get(0).getPath()))
                .ifPresent(fcmException(notification -> this.firebaseMessageAsyncUtils.sendMessage(
                        Collections.singletonMap(MobileScreen.CAMPAIGN.getScreen(), campaign.getId().toString()),
                        notification)));
    }

    public void sendNotificationEnterprise(Campaign campaign, Account enterprise, long quantity) {
        this.accountTokenRepository
                .getAccountTokenByAccountOptional(enterprise.getId())
                .map(List::stream)
                .orElseGet(Stream::empty)
                .findFirst()
                .map(token -> new PushNotificationRequest(
                        "The Finished Campaign",
                        "The Campaign name: " + campaign.getName() + ",with quantity sold: " + quantity,
                        "The Finished Campaign",
                        token.getRegistrationToken(),
                        campaign.getImage().get(0).getPath()))
                .ifPresent(fcmException(notification -> firebaseMessageAsyncUtils.sendMessage(
                        Collections.singletonMap(MobileScreen.CAMPAIGN.getScreen(), campaign.getId().toString()),
                        notification
                )));

    }
}
