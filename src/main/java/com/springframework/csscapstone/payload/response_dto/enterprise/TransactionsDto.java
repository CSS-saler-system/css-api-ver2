package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.springframework.csscapstone.data.status.TransactionStatus;
import com.springframework.csscapstone.payload.basic.BillImageBasicDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class TransactionsDto implements Serializable {
    private final UUID id;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createTransactionDate;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime lastModifiedDate;

    private final List<BillImageBasicDto> billImage;
    private final double point;
    private final TransactionStatus transactionStatus;
    private final AccountInnerTransactionResDto transactionCreator;
    private final AccountInnerTransactionResDto transactionApprover;

    public TransactionsDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("createTransactionDate") LocalDateTime createTransactionDate,
            @JsonProperty("lastModifiedDate") LocalDateTime lastModifiedDate,
            @JsonProperty("billImage") List<BillImageBasicDto> billImage,
            @JsonProperty("point") double point,
            @JsonProperty("transactionStatus") TransactionStatus transactionStatus,
            @JsonProperty("transactionCreator") AccountInnerTransactionResDto transactionCreator,
            @JsonProperty("transactionApprover") AccountInnerTransactionResDto transactionApprover) {
        this.id = id;
        this.createTransactionDate = createTransactionDate;
        this.lastModifiedDate = lastModifiedDate;
        this.billImage = billImage;
        this.point = point;
        this.transactionStatus = transactionStatus;
        this.transactionCreator = transactionCreator;
        this.transactionApprover = transactionApprover;
    }

    @Data
    public static class AccountInnerTransactionResDto implements Serializable {
        private final UUID id;
        private final String name;
        private final String email;
        private final Double point;

        public AccountInnerTransactionResDto(
                @JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("email") String email,
                @JsonProperty("point") Double point) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.point = point;
        }
    }
}
