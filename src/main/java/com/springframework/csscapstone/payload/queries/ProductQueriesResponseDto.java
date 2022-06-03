package com.springframework.csscapstone.payload.queries;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class ProductQueriesResponseDto implements Serializable {
    private final UUID id;
    private final String name;
    private final String shortDescription;
    private final Double price;
    private final Double pointSale;
    private final Long quantitySale;

    @JsonCreator(mode = PROPERTIES)

    public ProductQueriesResponseDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("short_description") String shortDescription,
            @JsonProperty("price") Double price,
            @JsonProperty("point_sale") Double pointSale,
            @JsonProperty("quantity_sale") Long quantitySale) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.price = price;
        this.pointSale = pointSale;
        this.quantitySale = quantitySale;
    }
}
