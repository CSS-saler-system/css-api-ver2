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
    private final AccountInnerFeedBack creator;

    @JsonCreator(mode = PROPERTIES)
    public FeedBackCreatorReqDto(
            @JsonProperty("feedContent") String feedContent,
            @JsonProperty("creator") AccountInnerFeedBack creator) {
        this.feedContent = feedContent;
        this.creator = creator;
    }

    @Data
    public static class AccountInnerFeedBack implements Serializable {
        private final UUID id;

        @JsonCreator(mode = PROPERTIES)
        public AccountInnerFeedBack(@JsonProperty("id") UUID id) {
            this.id = id;
        }
    }

}
