package com.springframework.csscapstone.payload.request_dto.collaborator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;
@Data
public class OrderCreatorReqDto implements Serializable {
    private final AccountDto account;
    private final CustomerDto customer;
    private final String deliveryAddress;
    private final String deliveryPhone;
    private final List<OrderDetailInnerCreatorDto> orderDetails;

    @JsonCreator(mode = PROPERTIES)
    public OrderCreatorReqDto(
            @JsonProperty("account") AccountDto account,
            @JsonProperty("customer") CustomerDto customer,
            @JsonProperty("deliveryAddress") String deliveryAddress,
            @JsonProperty("deliveryPhone") String deliveryPhone,
            @JsonProperty("orderDetails") List<OrderDetailInnerCreatorDto> orderDetails) {
        this.account = account;
        this.customer = customer;
        this.deliveryAddress = deliveryAddress;
        this.deliveryPhone = deliveryPhone;
        this.orderDetails = orderDetails;
    }

    @Data
    public static class OrderDetailInnerCreatorDto implements Serializable {
        private final Long quantity;
        private final ProductDto product;

        @JsonCreator(mode = PROPERTIES)
        public OrderDetailInnerCreatorDto(
                @JsonProperty("quantity") Long quantity,
                @JsonProperty("product") ProductDto product) {
            this.quantity = quantity;
            this.product = product;
        }

        @Data
        public static class ProductDto implements Serializable {
            private final UUID productId;

            @JsonCreator(mode = PROPERTIES)
            public ProductDto(@JsonProperty("productId") UUID productId) {
                this.productId = productId;
            }
        }
    }

    @Data
    public static class AccountDto implements Serializable {
        private final UUID accountId;

        @JsonCreator(mode = PROPERTIES)
        public AccountDto(@JsonProperty("accountId") UUID accountId) {
            this.accountId = accountId;
        }
    }

    @Data
    public static class CustomerDto implements Serializable {
        private final UUID customerId;

        @JsonCreator(mode = PROPERTIES)
        public CustomerDto(@JsonProperty("customerId") UUID customerId) {
            this.customerId = customerId;
        }
    }
}
