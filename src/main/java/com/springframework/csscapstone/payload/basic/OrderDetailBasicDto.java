package com.springframework.csscapstone.payload.basic;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class OrderDetailBasicDto implements Serializable {
    private final UUID id;
    private final String nameProduct;
    private final Long quantity;
    private final double orderLinePrice;
}
