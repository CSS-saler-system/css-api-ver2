package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class TransactionsResDto implements Serializable {
    private final UUID id;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private final LocalDateTime createTransaction;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private final LocalDateTime lastModifiedDate;
    private final List<BillImageBasicDto> billImage;
    private final double point;
    private final TransactionStatus transactionStatus;
    private final Set<AccountInnerTransactionsResDto> account;

    @JsonCreator(mode = PROPERTIES)
    public TransactionsResDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("createTransaction") LocalDateTime createTransaction,
            @JsonProperty("lastModifiedDate") LocalDateTime lastModifiedDate,
            @JsonProperty("billImage") List<BillImageBasicDto> billImage,
            @JsonProperty("point") double point,
            @JsonProperty("transactionStatus") TransactionStatus transactionStatus,
            @JsonProperty("account") Set<AccountInnerTransactionsResDto> account) {
        this.id = id;
        this.createTransaction = createTransaction;
        this.lastModifiedDate = lastModifiedDate;
        this.billImage = billImage;
        this.point = point;
        this.transactionStatus = transactionStatus;
        this.account = account;
    }

    @Data
    public static class AccountInnerTransactionsResDto implements Serializable {
        private final UUID id;
        private final String name;
        private final RoleInnerTransactionResDto role;

        @JsonCreator(mode = PROPERTIES)
        public AccountInnerTransactionsResDto(
                @JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("role") RoleInnerTransactionResDto role) {
            this.id = id;
            this.name = name;
            this.role = role;
        }

        @Data
        public static class RoleInnerTransactionResDto implements Serializable {
            private final String id;
            private final String name;
            @JsonCreator(mode = PROPERTIES)
            public RoleInnerTransactionResDto(
                    @JsonProperty("id") String id,
                    @JsonProperty("name") String name) {
                this.id = id;
                this.name = name;
            }
        }
    }
}
