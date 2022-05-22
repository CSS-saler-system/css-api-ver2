package com.springframework.csscapstone.payload.request_dto.admin;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailUpdater {
    private final UUID idProduct;
    private final Long quantity;
}
