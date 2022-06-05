package com.springframework.csscapstone.payload.request_dto.admin;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailCreatorReqDto {
    private final UUID idProduct;
    //greater than 0
    private final Long quantity;
//    private final double orderLinePrice;
    private final _Order order;

    @Data
    public static class _Order {
        private final UUID id;
    }

}
