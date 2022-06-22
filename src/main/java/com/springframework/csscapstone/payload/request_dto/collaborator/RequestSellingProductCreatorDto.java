package com.springframework.csscapstone.payload.request_dto.collaborator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class RequestSellingProductCreatorDto implements Serializable {

    private final ProductInnerRequestCreatorDto product;
    private final AccountInnerRequestCreatorDto collaborator;

    @JsonCreator(mode = PROPERTIES)
    public RequestSellingProductCreatorDto(
            @JsonProperty("product") ProductInnerRequestCreatorDto product,
            @JsonProperty("collaborator") AccountInnerRequestCreatorDto collaborator) {
        this.product = product;
        this.collaborator = collaborator;
    }

    @Data
    public static class ProductInnerRequestCreatorDto implements Serializable {
        private final UUID productId;

        @JsonCreator(mode = PROPERTIES)
        public ProductInnerRequestCreatorDto(@JsonProperty("productId") UUID productId) {
            this.productId = productId;
        }
    }

    @Data
    public static class AccountInnerRequestCreatorDto implements Serializable {
        private final UUID accountId;

        public AccountInnerRequestCreatorDto(@JsonProperty("accountId") UUID accountId) {
            this.accountId = accountId;
        }
    }
}
