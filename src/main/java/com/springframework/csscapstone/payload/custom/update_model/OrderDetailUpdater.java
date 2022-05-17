package com.springframework.csscapstone.payload.custom.update_model;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailUpdater {
    private final UUID idProduct;
    private final Long quantity;
}
