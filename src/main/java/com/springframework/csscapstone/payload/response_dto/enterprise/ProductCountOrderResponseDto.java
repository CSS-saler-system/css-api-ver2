package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.basic.ProductImageDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class ProductCountOrderResponseDto implements Serializable {
    private final UUID id;
    private final String name;
    private final String shortDescription;
    private final String description;
    private final Long quantityInStock;
    private final Double price;
    private final Double pointSale;
    private final ProductStatus productStatus;
    private final List<ProductImageDto> image;
    private final Long quantity;

    @JsonCreator(mode = PROPERTIES)
    public ProductCountOrderResponseDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("short_description") String shortDescription,
            @JsonProperty("description") String description,
            @JsonProperty("quantity_in_stock") Long quantityInStock,
            @JsonProperty("price") Double price,
            @JsonProperty("point_sale") Double pointSale,
            @JsonProperty("product_status") ProductStatus productStatus,
            @JsonProperty("images") List<ProductImageDto> image,
            @JsonProperty("number_order") Long quantity) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.quantityInStock = quantityInStock;
        this.price = price;
        this.pointSale = pointSale;
        this.productStatus = productStatus;
        this.image = image;
        this.quantity = quantity;
    }
}
