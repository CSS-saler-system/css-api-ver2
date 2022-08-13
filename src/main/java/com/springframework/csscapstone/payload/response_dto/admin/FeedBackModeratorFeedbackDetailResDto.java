package com.springframework.csscapstone.payload.response_dto.admin;

import com.springframework.csscapstone.data.status.FeedbackStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class FeedBackModeratorFeedbackDetailResDto implements Serializable {
    private final UUID id;
    private final String feedReply;
    private final String feedContent;
    private final ApproverDto approver;
    private final CreatorDto creator;
    private final LocalDateTime createDate;
    private final LocalDateTime replyDate;
    private final FeedbackStatus feedbackStatus;

    @Data
    public static class ApproverDto implements Serializable {
        private final UUID id;
        private final String name;
    }

    @Data
    public static class CreatorDto implements Serializable {
        private final String name;
        private final String phone;
        private final String email;
        private final RoleDto role;

        @Data
        public static class RoleDto implements Serializable {
            private final String name;
        }
    }
}
