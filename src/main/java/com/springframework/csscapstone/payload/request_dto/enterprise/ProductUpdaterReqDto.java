package com.springframework.csscapstone.payload.request_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class ProductUpdaterReqDto {
    @NotEmpty(message = "The name is must be not empty")
    private final String name;
    @NotEmpty(message = "The branch is must be not empty")
    private final String brand;
    @NotEmpty(message = "The short_description is must be not empty")
    private final String shortDescription;
    @NotEmpty(message = "The description is must be not empty")
    private final String description;
    @NotNull(message = "The price is must be not empty")
    private final Double price;
    @NotNull(message = "The point_sale is must be not empty")
    private final Double pointSale;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProductUpdaterReqDto(
            @JsonProperty("name") String name,
            @JsonProperty("brand") String brand,
            @JsonProperty("shortDescription") String shortDescription,
            @JsonProperty("description") String description,
            @JsonProperty("price") Double price,
            @JsonProperty("pointSale") Double pointSale) {
        this.name = name;
        this.brand = brand;
        this.shortDescription = shortDescription;
        this.description = description;
        this.price = price;
        this.pointSale = pointSale;
    }
}
