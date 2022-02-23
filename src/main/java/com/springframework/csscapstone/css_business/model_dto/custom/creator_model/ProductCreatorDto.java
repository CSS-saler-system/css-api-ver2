package com.springframework.csscapstone.css_business.model_dto.custom.creator_model;

import com.springframework.csscapstone.css_data.status.ProductStatus;
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
