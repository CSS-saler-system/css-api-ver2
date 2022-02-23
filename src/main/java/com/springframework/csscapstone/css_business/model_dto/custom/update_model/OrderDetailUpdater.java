package com.springframework.csscapstone.css_business.model_dto.custom.update_model;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailUpdater {
    private final UUID idProduct;
    private final Long quantity;
}
