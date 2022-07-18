package com.springframework.csscapstone.payload.response_dto.admin;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class ProductForModeratorResDto implements Serializable {
    private final UUID id;
    private final String name;
    private final String brand;
    private final String shortDescription;
    private final AccountDto account;

    @Data
    public static class AccountDto implements Serializable {
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
