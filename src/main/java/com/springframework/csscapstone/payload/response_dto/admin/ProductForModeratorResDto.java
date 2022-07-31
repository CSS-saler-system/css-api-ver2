package com.springframework.csscapstone.payload.response_dto.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.status.ProductStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class ProductForModeratorResDto implements Serializable {
    private final UUID id;
    private final String name;
    private final String brand;
    private final String shortDescription;
    private final ProductStatus productStatus;
    private final AccountDto account;

    @JsonCreator(mode = PROPERTIES)
    public ProductForModeratorResDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("brand") String brand,
            @JsonProperty("shortDescription") String shortDescription,
            @JsonProperty("status") ProductStatus productStatus,
            @JsonProperty("account") AccountDto account) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.shortDescription = shortDescription;
        this.productStatus = productStatus;
        this.account = account;
    }

    @Data
    public static class AccountDto implements Serializable {
        private final UUID id;
        private final String name;
        private final RoleDto role;

        @JsonCreator(mode = PROPERTIES)
        public AccountDto(
                @JsonProperty("id") UUID id, @JsonProperty("name") String name, @JsonProperty("role") RoleDto role) {
            this.id = id;
            this.name = name;
            this.role = role;
        }

        @Data
        public static class RoleDto implements Serializable {
            private final String id;
            private final String name;

            @JsonCreator(mode = PROPERTIES)
            public RoleDto(@JsonProperty("id") String id, @JsonProperty("name") String name) {
                this.id = id;
                this.name = name;
            }
        }
    }

}
