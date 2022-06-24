package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.springframework.csscapstone.data.status.TransactionStatus;
import com.springframework.csscapstone.payload.basic.BillImageBasicDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class TransactionsResDto implements Serializable {
    private final UUID id;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private final LocalDateTime createTransactionDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private final LocalDateTime LastModifiedDate;

    private final List<BillImageBasicDto> billImage;
    private final double point;
    private final TransactionStatus transactionStatus;
    private final AccountDto transactionCreator;
    private final AccountDto transactionApprover;

    @Data
    public static class AccountDto implements Serializable {
        private final UUID id;
        private final String name;
        private final String email;
    }
}
