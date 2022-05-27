package com.springframework.csscapstone.payload.request_dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.status.ProductStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * Should be add more field like
 * min quantity,
 * max quantity,
 * minWeight, maxWeight,
 * minPoint, maxPoint
 * minPrice, maxPrice
 * LocalDate start, LocalDate exp
 * String Color
 */
@Data
public class ProductSearchListDto implements Serializable {
    private final String name;
    private final String brand;
    private final Double weight;
    private final Long quantityInStock;
    private final Double price;
    private final Double pointSale;
    private final ProductStatus productStatus;

    public ProductSearchListDto(
            @JsonProperty("name") String name,
            @JsonProperty("brand") String brand,
            @JsonProperty("weight") Double weight,
            @JsonProperty("quantity") Long quantityInStock,
            @JsonProperty("price") Double price,
            @JsonProperty("point_sale") Double pointSale,
            @JsonProperty("product_status") ProductStatus productStatus) {
        this.name = name;
        this.brand = brand;
        this.weight = weight;
        this.quantityInStock = quantityInStock;
        this.price = price;
        this.pointSale = pointSale;
        this.productStatus = productStatus;
    }
}