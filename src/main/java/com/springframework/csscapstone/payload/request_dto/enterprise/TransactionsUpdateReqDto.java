package com.springframework.csscapstone.payload.request_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.status.TransactionStatus;
import com.springframework.csscapstone.payload.basic.BillImageBasicDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class TransactionsUpdateReqDto {
    private final double point;
    private final AccountNestedDto creator;

    @JsonCreator(mode = PROPERTIES)
    public TransactionsUpdateReqDto(
            @JsonProperty("point") double point,
            @JsonProperty("creator") AccountNestedDto creator) {
        this.point = point;
        this.creator = creator;
    }

    @Data
    public static class AccountNestedDto implements Serializable {
        private final UUID id;
        private final String name;
        private final RoleDto role;

        @JsonCreator(mode = PROPERTIES)
        public AccountNestedDto(@JsonProperty("id") UUID id, @JsonProperty("name") String name, @JsonProperty("role") RoleDto role) {
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
