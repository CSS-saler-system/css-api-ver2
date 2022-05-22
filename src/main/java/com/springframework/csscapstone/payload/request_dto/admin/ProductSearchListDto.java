package com.springframework.csscapstone.payload.request_dto.admin;

import com.springframework.csscapstone.data.status.ProductStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * Should be add more field like
 * min quantity, max quantity,
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
}
