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
    private final Set<AccountDto> account;

    @JsonCreator(mode = PROPERTIES)
    public TransactionsResDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("create_date") LocalDateTime createTransaction,
            @JsonProperty("modified_date") LocalDateTime lastModifiedDate,
            @JsonProperty("images") List<BillImageBasicDto> billImage,
            @JsonProperty("point") double point,
            @JsonProperty("transaction") TransactionStatus transactionStatus,
            @JsonProperty("account") Set<AccountDto> account) {
        this.id = id;
        this.createTransaction = createTransaction;
        this.lastModifiedDate = lastModifiedDate;
        this.billImage = billImage;
        this.point = point;
        this.transactionStatus = transactionStatus;
        this.account = account;
    }

    @Data
    public static class AccountDto implements Serializable {
        private final UUID id;
        private final String name;
        private final RoleDto role;

        @JsonCreator(mode = PROPERTIES)
        public AccountDto(
                @JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("role") RoleDto role) {
            this.id = id;
            this.name = name;
            this.role = role;
        }

        @Data
        public static class RoleDto implements Serializable {
            private final String id;
            private final String name;
            @JsonCreator(mode = PROPERTIES)
            public RoleDto(
                    @JsonProperty("id") String id,
                    @JsonProperty("name") String name) {
                this.id = id;
                this.name = name;
            }
        }
    }
}
