package com.springframework.csscapstone.config.firebase_config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.springframework.csscapstone.config.firebase_config.model.NotificationParameter;
import com.springframework.csscapstone.config.firebase_config.model.PushNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class FirebaseMessageService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * todo send message
     * noti request for collaborator
     * complement win campaign for collab
     * @param data
     * @param request
     * @throws JsonProcessingException
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Async("threadPoolTaskExecutor")
    public void sendMessage(Map<String, String> data, PushNotificationRequest request)
            throws JsonProcessingException, ExecutionException, InterruptedException {
        //get message from preconfigured with data
        Message message = getPreconfiguredMessageWithData(data, request);
        System.out.println("before:  " + message);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        String jsonOutput = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(message);
        System.out.println("after:  " + jsonOutput);

        String res = sendAndGetResponse(message);
        LOGGER.info("Sent message with data. Topic: " + request.getTopic()
                + ", " + res + " msg " + jsonOutput);
    }

    /**
     * todo assign map data into message
     * @param data
     * @param request
     * @return
     */
    private Message getPreconfiguredMessageWithData(Map<String, String> data, PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request)
                .putAllData(data)
                .setToken(request.getToken())
                .build();
    }

    /**
     * todo config for android and apple device receive message
     * @param request
     * @return
     */
    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {

        //android-specific service
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());

        //apple push notification service
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());

        return Message.builder()
                .setAndroidConfig(androidConfig)
                .setApnsConfig(apnsConfig)

                //request.getTitle(), request.getMessage()
                .setNotification(Notification.builder()
                        .setTitle(request.getTitle())
                        .setBody(request.getMessage())
                        .build());
    }

    private Message getPreconfiguredMessageToToken(PushNotificationRequest request) {
        return getPreconfiguredMessageBuilder(request)
                .setToken(request.getToken())
                .build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder().setCategory(topic).setThreadId(topic).build()).build();
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis())
                .setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder()
                        .setSound(NotificationParameter.SOUND.getValue())
                        .setColor(NotificationParameter.COLOR.getValue())
                        .setTag(topic).build()).build();
    }

    //rarely using
    private Message getPreconfiguredMessageWithoutData(PushNotificationRequest request) {
        return null;
    }


    public void sendMessageWithoutData(PushNotificationRequest request) throws ExecutionException, InterruptedException {
        Message message = getPreconfiguredMessageWithoutData(
                request);
        String res = sendAndGetResponse(message);
        LOGGER.info("Sent message without data. Topic: " + request.getTopic() + " , " + res);
    }

    public void sendMessageToToken(PushNotificationRequest request) throws JsonProcessingException, ExecutionException, InterruptedException {
        Message message = getPreconfiguredMessageToToken(request);
        String jsonOutput = this.objectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(message);
        String response = sendAndGetResponse(message);
        LOGGER.info("Sent message to token. Device token: " + request.getToken() +
                ", " + response + " msg " + jsonOutput);
    }

    private String sendAndGetResponse(Message message)
            throws ExecutionException, InterruptedException {
        return FirebaseMessaging.getInstance().sendAsync(message).get();
    }


}
