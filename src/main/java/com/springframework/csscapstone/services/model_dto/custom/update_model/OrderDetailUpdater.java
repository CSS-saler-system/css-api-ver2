package com.springframework.csscapstone.services.model_dto.custom.update_model;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailUpdater {
    private final UUID idProduct;
    private final Long quantity;
}
