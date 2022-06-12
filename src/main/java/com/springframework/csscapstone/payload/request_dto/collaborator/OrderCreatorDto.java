package com.springframework.csscapstone.payload.request_dto.collaborator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class OrderCreatorDto implements Serializable {

    //check existing
    @JsonProperty("account")
    private final AccountDto account;
    @JsonProperty("customer")
    //check existing
    private final CustomerDto customer;
    @JsonProperty("delivery_address")
    private final String deliveryAddress;
    @JsonProperty("delivery_phone")
    private final String deliveryPhone;
    //save new
    @JsonProperty("order_details")
    private final List<OrderDetailDto> orderDetails;
    @Data
    public static class OrderDetailDto implements Serializable {
        @JsonProperty("quantity")
        private final Long quantity;
        @JsonProperty("product")
        private final ProductDto product;

        @Data
        public static class ProductDto implements Serializable {
            @JsonProperty("id_product")
            private final UUID id;

        }
    }

    @Data
    public static class AccountDto implements Serializable {
        @JsonProperty("id_account")
        private final UUID id;
    }

    @Data
    public static class CustomerDto implements Serializable {
        @JsonProperty("id_customer")
        private final UUID id;
    }
}
