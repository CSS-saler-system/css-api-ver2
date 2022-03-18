package com.springframework.csscapstone.services.model_dto.basic;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class OrderDetailDto implements Serializable {
    private final UUID id;
    private final String nameProduct;
    private final Long quantity;
    private final double orderLinePrice;
}
