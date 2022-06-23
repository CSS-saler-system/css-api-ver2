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
            double point, AccountNestedTransactionDto creator) {
        this.point = point;
        this.creator = creator;
    }

    @Data
    public static class AccountNestedTransactionDto implements Serializable {
        private final UUID id;
        private final String name;
        private final RoleInnerTransactionCreatorDto role;

        @JsonCreator(mode = PROPERTIES)
        public AccountNestedTransactionDto(
                @JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("role") RoleInnerTransactionCreatorDto role) {
            this.id = id;
            this.name = name;
            this.role = role;
        }

        @Data
        public static class RoleInnerTransactionCreatorDto implements Serializable {
            private final String id;
            private final String name;

            public RoleInnerTransactionCreatorDto(
                    @JsonProperty("id") String id,
                    @JsonProperty("name") String name) {
                this.id = id;
                this.name = name;
            }
        }
    }
}
