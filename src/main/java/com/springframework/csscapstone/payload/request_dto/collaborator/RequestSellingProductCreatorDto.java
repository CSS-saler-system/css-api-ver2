package com.springframework.csscapstone.payload.request_dto.collaborator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class RequestSellingProductCreatorDto implements Serializable {

    private final ProductDto product;
    private final AccountDto account;

    @JsonCreator(mode = PROPERTIES)
    public RequestSellingProductCreatorDto(
            @JsonProperty("product") ProductDto product,
            @JsonProperty("account") AccountDto account) {
        this.product = product;
        this.account = account;
    }

    @Data
    public static class ProductDto implements Serializable {
        private final UUID id;

        @JsonCreator(mode = PROPERTIES)
        public ProductDto(@JsonProperty("id") UUID id) {
            this.id = id;
        }
    }

    @Data
    public static class AccountDto implements Serializable {
        private final UUID id;

        public AccountDto(UUID id) {
            this.id = id;
        }
    }
}
