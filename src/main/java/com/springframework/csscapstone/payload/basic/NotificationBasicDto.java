package com.springframework.csscapstone.payload.basic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class NotificationBasicDto implements Serializable {
    private final UUID id;
    private final String title;
    private final String message;
    private final LocalDateTime sendDate;
    private final String pathImage;
    private final AccountTokenDto accountToken;

    @JsonCreator(mode = PROPERTIES)
    public NotificationBasicDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("title") String title,
            @JsonProperty("message") String message,
            @JsonProperty("sendDate") LocalDateTime sendDate,
            @JsonProperty("pathImage") String pathImage,
            @JsonProperty("accountToken") AccountTokenDto accountToken) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.sendDate = sendDate;
        this.pathImage = pathImage;
        this.accountToken = accountToken;
    }

    @Data
    public static class AccountTokenDto implements Serializable {
        private final UUID id;
        private final String registrationToken;
        private final LocalDateTime updateTokenDate;
        private final AccountDto account;

        @JsonCreator(mode = PROPERTIES)
        public AccountTokenDto(
                @JsonProperty("id") UUID id,
                @JsonProperty("registrationToken") String registrationToken,
                @JsonProperty("updateTokenDate") LocalDateTime updateTokenDate,
                @JsonProperty("account") AccountDto account) {
            this.id = id;
            this.registrationToken = registrationToken;
            this.updateTokenDate = updateTokenDate;
            this.account = account;
        }

        @Data
        public static class AccountDto implements Serializable {
            private final UUID id;

            @JsonCreator(mode = PROPERTIES)
            public AccountDto(UUID id) {
                this.id = id;
            }
        }
    }
}
