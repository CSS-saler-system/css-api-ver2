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
    private final UUID id;
    private final String deliveryPhone;
    private final String deliveryAddress;
    private final List<OrderDetailDto> orderDetails;

    @JsonCreator(mode = PROPERTIES)
    public OrderUpdaterDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("delivery_phone") String deliveryPhone,
            @JsonProperty("delivery_address") String deliveryAddress,
            @JsonProperty("oder_details") List<OrderDetailDto> orderDetails) {
        this.id = id;
        this.deliveryPhone = deliveryPhone;
        this.deliveryAddress = deliveryAddress;
        this.orderDetails = orderDetails;
    }

    @Data
    public static class OrderDetailDto implements Serializable {
        private final Long quantity;
        private final ProductDto product;

        @JsonCreator(mode = PROPERTIES)
        public OrderDetailDto(
                @JsonProperty("quantity") Long quantity,
                @JsonProperty("product") ProductDto product) {
            this.quantity = quantity;
            this.product = product;
        }

        @Data
        public static class ProductDto implements Serializable {
            private final UUID id;

            @JsonCreator(mode = PROPERTIES)
            public ProductDto(UUID id) {
                this.id = id;
            }
        }
    }
}
