package com.springframework.csscapstone.payload.response_dto.collaborator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.springframework.csscapstone.data.status.RequestStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RequestSellingProductForCollResDto implements Serializable {
    private final UUID id;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime modifiedDate;
    private final RequestStatus requestStatus;
    private final ProductDto product;

    @Data
    public static class ProductDto implements Serializable {
        private final UUID id;
        private final String name;
        private final String brand;
        private final Double price;
        private final AccountDto account;

        @Data
        public static class AccountDto implements Serializable {
            private final UUID id;
            private final String name;
        }
    }
}
