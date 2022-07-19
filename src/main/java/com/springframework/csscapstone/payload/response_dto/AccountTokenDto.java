package com.springframework.csscapstone.payload.response_dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AccountTokenDto implements Serializable {
    private final UUID id;
    private final String registrationToken;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDateTime updateTokenDate;
    private final AccountDto account;

    @Data
    public static class AccountDto implements Serializable {
        private final UUID id;
        private final String name;
        private final RoleDto role;

        @Data
        public static class RoleDto implements Serializable {
            private final String id;
            private final String name;
        }
    }
}
