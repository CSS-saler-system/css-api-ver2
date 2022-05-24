package com.springframework.csscapstone.payload.request_dto.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
public class ProductCreatorDto implements Serializable {
    @NotNull(message = "The creator account id must be not null")
    private UUID creatorAccountId;

    @NotNull(message = "The category id must be not null")
    private UUID categoryId;

    @NotEmpty(message = "The name must be not empty")
    private final String name;

    @NotEmpty(message = "The name must be not empty")
    private final String brand;

    private final String shortDescription;

    @NotEmpty(message = "The description must be not empty")
    private final String description;

    @NotNull(message = "The quantity must be not null")
    private final Long quantity;

    @NotNull(message = "The point must be not null")
    private final Double price;

    @NotNull(message = "The point must be not null")
    private final Double pointSale;

//    @NotNull(message = "The type image not null")
//    private final MultipartFile[] typeImage;
//
//    @NotNull(message = "The type image not null")
//    private final MultipartFile[] certificationImage;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProductCreatorDto(
            @JsonProperty UUID creatorAccountId,
            @JsonProperty UUID categoryId,
            @JsonProperty String name,
            @JsonProperty String brand,
            @JsonProperty String shortDescription,
            @JsonProperty String description,
            @JsonProperty Long quantity,
            @JsonProperty Double price,
            @JsonProperty Double pointSale
    ) { this.creatorAccountId = creatorAccountId;
        this.categoryId = categoryId;
        this.name = name;
        this.brand = brand;
        this.shortDescription = shortDescription;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.pointSale = pointSale;
    }
}
