package com.springframework.csscapstone.payload.request_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class TransactionsCreatorReqDto implements Serializable {
    //    private final List<BillImageBasicDto> billImage;
    private final double point;
    private final AccountNestedTransactionDto creator;

    @JsonCreator(mode = PROPERTIES)
    public TransactionsCreatorReqDto(
            @JsonProperty("point") double point,
            @JsonProperty("creator") AccountNestedTransactionDto creator) {
        this.point = point;
        this.creator = creator;
    }

    @Data
    public static class AccountNestedTransactionDto implements Serializable {
        private final UUID accountId;

        @JsonCreator(mode = PROPERTIES)
        public AccountNestedTransactionDto(
                @JsonProperty("accountId") UUID accountId) {
            this.accountId = accountId;
        }

    }

    @Override
    public String toString() {
        return "TransactionsCreatorReqDto{" +
                "point=" + point +
                ", creator=" + creator +
                '}';
    }
}
