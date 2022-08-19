package com.springframework.csscapstone.payload.response_dto.collaborator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.status.FeedbackStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class FeedBackCollaboratorListDto implements Serializable {
    private final UUID id;
    private final LocalDateTime createDate;
    private final LocalDateTime replyDate;
    private final FeedbackStatus feedbackStatus;

    @JsonCreator(mode = PROPERTIES)
    public FeedBackCollaboratorListDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("createDate") LocalDateTime createDate,
            @JsonProperty("replyDate") LocalDateTime replyDate,
            @JsonProperty("feedbackStatus") FeedbackStatus feedbackStatus) {
        this.id = id;
        this.createDate = createDate;
        this.replyDate = replyDate;
        this.feedbackStatus = feedbackStatus;
    }
}
