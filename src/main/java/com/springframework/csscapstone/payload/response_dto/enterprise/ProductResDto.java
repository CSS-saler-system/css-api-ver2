package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.basic.ProductImageBasicDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class ProductResDto implements Serializable {
    private final UUID id;
    private final String name;
    private final String brand;
    private final String shortDescription;
    private final Double price;
    private final Double pointSale;
    private final ProductStatus productStatus;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProductResDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("brand") String brand,
            @JsonProperty("shortDescription") String shortDescription,
            @JsonProperty("price") Double price,
            @JsonProperty("pointSale") Double pointSale,
            @JsonProperty("productStatus") ProductStatus productStatus
    ) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.shortDescription = shortDescription;
        this.price = price;
        this.pointSale = pointSale;
        this.productStatus = productStatus;
    }
}
