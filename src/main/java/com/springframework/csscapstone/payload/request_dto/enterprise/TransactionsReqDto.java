package com.springframework.csscapstone.payload.request_dto.enterprise;

import com.springframework.csscapstone.payload.basic.BillImageBasicDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class TransactionsReqDto implements Serializable {
    private final List<BillImageBasicDto> billImage;
    private final double point;
    private final Set<AccountNestedDto> account;

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
