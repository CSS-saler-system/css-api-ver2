package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class ProductWithQuantityDTO implements Serializable {

    private final UUID id;
    private final String name;
    private final String brand;
    private final String shortDescription;
    private final String description;
    private final Long quantityInStock;
    private final Double price;
    private final Double pointSale;

    private final Integer count;

    public ProductWithQuantityDTO(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("brand") String brand,
            @JsonProperty("short_description") String shortDescription,
            @JsonProperty("description") String description,
            @JsonProperty("quantity_in_stock") Long quantityInStock,
            @JsonProperty("price") Double price,
            @JsonProperty("point_sale") Double pointSale,
            @JsonProperty("count") Integer count) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.shortDescription = shortDescription;
        this.description = description;
        this.quantityInStock = quantityInStock;
        this.price = price;
        this.pointSale = pointSale;
        this.count = count;
    }
}
