package com.springframework.csscapstone.services.model_dto.custom.creator_model;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailCreator {
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
