package com.springframework.csscapstone.payload.response_dto.admin;

import com.springframework.csscapstone.data.status.FeedbackStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class FeedBackPageModeraterResDto implements Serializable {
    private final UUID id;
    private final AccountDto creator;
    private final LocalDateTime createDate;
    private final LocalDateTime replyDate;
    private final FeedbackStatus feedbackStatus;

    @Data
    public static class AccountDto implements Serializable {
        private final UUID id;
        private final String name;
        private final RoleDto role;

        @Data
        public static class RoleDto implements Serializable {
            private final String name;
        }
    }
}
