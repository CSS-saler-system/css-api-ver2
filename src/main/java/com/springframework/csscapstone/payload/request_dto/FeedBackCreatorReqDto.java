package com.springframework.csscapstone.payload.request_dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class FeedBackCreatorReqDto implements Serializable {
    private final String feedContent;
    private final AccountDto creator;

    @JsonCreator(mode = PROPERTIES)
    public FeedBackCreatorReqDto(
            @JsonProperty("feedContent") String feedContent,
            @JsonProperty("creator") AccountDto creator) {
        this.feedContent = feedContent;
        this.creator = creator;
    }

    @Data
    public static class AccountDto implements Serializable {
        private final UUID id;

        @JsonCreator(mode = PROPERTIES)
        public AccountDto(UUID id) {
            this.id = id;
        }
    }

    @Data
    public static class CampaignDto implements Serializable {
        private final UUID id;

        @JsonCreator(mode = PROPERTIES)
        public CampaignDto(UUID id) {
            this.id = id;
        }
    }
}
