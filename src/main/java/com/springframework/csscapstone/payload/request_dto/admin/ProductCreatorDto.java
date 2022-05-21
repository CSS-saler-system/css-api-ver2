package com.springframework.csscapstone.payload.request_dto.admin;

import com.springframework.csscapstone.data.status.ProductStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductCreatorDto {
    private UUID accountId;

    private String name;
    private String brand;
    private Double weight;
    private String shortDescription;
    private String description;
    private Long quantity;
    private Double price;
    private Double pointSale;
    private ProductStatus status = ProductStatus.IN_STOCK;
}
