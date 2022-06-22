package com.springframework.csscapstone.payload.request_dto.collaborator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class OrderUpdaterDto implements Serializable {

    public final UUID idCollaborator;
    private final AccountDto account;
    private final CustomerDto customer;
    private final String deliveryAddress;
    private final String deliveryPhone;
    private final List<OrderDetailInnerUpdaterDto> orderDetails;

    //    @JsonCreator(mode = PROPERTIES)
    public OrderUpdaterDto(
            @JsonProperty("idCollaborator") UUID idCollaborator,
            @JsonProperty("account") AccountDto account,
            @JsonProperty("customer") CustomerDto customer,
            @JsonProperty("deliveryAddress") String deliveryAddress,
            @JsonProperty("deliveryPhone") String deliveryPhone,
            @JsonProperty("orderDetails") List<OrderDetailInnerUpdaterDto> orderDetails) {
        this.idCollaborator = idCollaborator;
        this.account = account;
        this.customer = customer;
        this.deliveryAddress = deliveryAddress;
        this.deliveryPhone = deliveryPhone;
        this.orderDetails = orderDetails;
    }

    @Data
    public static class OrderDetailInnerUpdaterDto implements Serializable {
        private final UUID idOrderDetails;
        private final Long quantity;
        private final ProductDto product;

        @JsonCreator(mode = PROPERTIES)
        public OrderDetailInnerUpdaterDto(
                @JsonProperty("idOrderDetails") UUID idOrderDetails,
                @JsonProperty("quantity") Long quantity,
                @JsonProperty("product") ProductDto product) {
            this.idOrderDetails = idOrderDetails;
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
