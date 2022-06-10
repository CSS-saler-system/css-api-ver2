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
    private final LocalDateTime LastModifiedDate;
    private final List<BillImageBasicDto> billImage;
    private final TransactionStatus transactionStatus;
    private final Set<AccountDto> account;
    @JsonCreator(mode = PROPERTIES)
    public TransactionsResDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("create_date_transaction") LocalDateTime createTransaction,
            @JsonProperty("last_modified_date") LocalDateTime lastModifiedDate,
            @JsonProperty("bill_image") List<BillImageBasicDto> billImage,
            @JsonProperty("status") TransactionStatus transactionStatus,
            @JsonProperty("accounts") Set<AccountDto> account) {
        this.id = id;
        this.createTransaction = createTransaction;
        LastModifiedDate = lastModifiedDate;
        this.billImage = billImage;
        this.transactionStatus = transactionStatus;
        this.account = account;
    }

    @Data
    public static class AccountDto implements Serializable {
        private final UUID id;
        private final String name;
        private final RoleInnerDto role;


        public static class RoleInnerDto {
            private final String role;

            public RoleInnerDto(String role) {
                this.role = role;
            }
        }

        @JsonCreator(mode = PROPERTIES)
        public AccountDto(
                @JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("role") RoleInnerDto role) {
            this.id = id;
            this.name = name;
            this.role = role;
        }
    }
}
