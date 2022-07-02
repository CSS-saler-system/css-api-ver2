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
    private final String description;
//    private final Long quantity;
    private final Double price;
    private final Double pointSale;
    private final ProductStatus productStatus;
    private final List<ProductImageBasicDto> image;
    private final CategoryInnerProductResDto category;
    private final AccountInnerProductResDto account;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProductResDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("brand") String brand,
            @JsonProperty("shortDescription") String shortDescription,
            @JsonProperty("description") String description,
//            @JsonProperty("quantity") Long quantity,
            @JsonProperty("price") Double price,
            @JsonProperty("pointSale") Double pointSale,
            @JsonProperty("productStatus") ProductStatus productStatus,
            @JsonProperty("certificationImages") List<ProductImageBasicDto> image,
            @JsonProperty("category") CategoryInnerProductResDto category,
            @JsonProperty("account") AccountInnerProductResDto account) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.shortDescription = shortDescription;
        this.description = description;
//        this.quantity = quantity;
        this.price = price;
        this.pointSale = pointSale;
        this.productStatus = productStatus;
        this.image = image;
        this.category = category;
        this.account = account;
    }


    @Data
    public static class CategoryInnerProductResDto implements Serializable {
        private final UUID id;
        private final String categoryName;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public CategoryInnerProductResDto(@JsonProperty("id") UUID id, @JsonProperty("category_name") String categoryName) {
            this.id = id;
            this.categoryName = categoryName;
        }
    }

    @Data
    public static class AccountInnerProductResDto implements Serializable {
        private final UUID id;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public AccountInnerProductResDto(@JsonProperty("id") UUID id) {
            this.id = id;
        }
    }
}
