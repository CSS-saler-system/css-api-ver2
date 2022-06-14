package com.springframework.csscapstone.payload.request_dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.status.TransactionStatus;
import com.springframework.csscapstone.payload.basic.BillImageBasicDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.TransactionsCreatorReqDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class TransactionsUpdateReqDto {
    private final UUID id;
    private final List<BillImageBasicDto> billImage;
    private final double point;
    private final Set<AccountNestedDto> account;

    private final TransactionStatus status;

    public TransactionsUpdateReqDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("bill_images") List<BillImageBasicDto> billImage,
            @JsonProperty("point") double point,
            @JsonProperty("accounts") Set<AccountNestedDto> account,
            @JsonProperty("status") TransactionStatus status) {
        this.id = id;
        this.billImage = billImage;
        this.point = point;
        this.account = account;
        this.status = status;
    }

    @Data
    public static class AccountNestedDto implements Serializable {
        private final UUID id;
        private final String name;
        private final RoleDto role;

        @Data
        public static class RoleDto implements Serializable {
            private final String id;
            private final String name;
        }
    }
}
