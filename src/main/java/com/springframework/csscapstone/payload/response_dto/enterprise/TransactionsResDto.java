package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private final UUID transactionId;

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
    private final AccountInnerTransactionResDto transactionCreator;
    private final AccountInnerTransactionResDto transactionApprover;

    public TransactionsResDto(
            @JsonProperty("transactionId") UUID transactionId,
            @JsonProperty("createTransactionDate") LocalDateTime createTransactionDate,
            @JsonProperty("lastModifiedDate") LocalDateTime lastModifiedDate,
            @JsonProperty("billImage") List<BillImageBasicDto> billImage,
            @JsonProperty("point") double point,
            @JsonProperty("transactionStatus") TransactionStatus transactionStatus,
            @JsonProperty("transactionCreator") AccountInnerTransactionResDto transactionCreator,
            @JsonProperty("transactionApprover") AccountInnerTransactionResDto transactionApprover) {
        this.transactionId = transactionId;
        this.createTransactionDate = createTransactionDate;
        LastModifiedDate = lastModifiedDate;
        this.billImage = billImage;
        this.point = point;
        this.transactionStatus = transactionStatus;
        this.transactionCreator = transactionCreator;
        this.transactionApprover = transactionApprover;
    }

    @Data
    public static class AccountInnerTransactionResDto implements Serializable {
        private final UUID accountId;
        private final String name;
        private final String email;

        public AccountInnerTransactionResDto(
                @JsonProperty("accountId") UUID accountId,
                @JsonProperty("name") String name,
                @JsonProperty("email") String email) {
            this.accountId = accountId;
            this.name = name;
            this.email = email;
        }
    }
}
