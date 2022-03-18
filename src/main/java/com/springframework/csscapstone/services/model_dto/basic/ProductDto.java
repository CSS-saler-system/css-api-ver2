package com.springframework.csscapstone.services.model_dto.basic;

import com.springframework.csscapstone.data.status.ProductStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class ProductDto implements Serializable {
    private final UUID id;
    private final String name;
    private final String brand;
    private final double weight;
    private final String shortDescription;
    private final String description;
    private final Long quantityInStock;
    private final double price;
    private final double pointSale;
    private final ProductStatus productStatus;
}
