package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.status.CategoryStatus;
import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.basic.ProductImageBasicDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class ProductDetailEnterpriseDto implements Serializable {
    private final UUID id;
    private final String name;
    private final String brand;
    private final String shortDescription;
    private final String description;
    private final Double price;
    private final Double pointSale;
    private final ProductStatus productStatus;
    private final List<ProductImageBasicDto> normal;
    private final List<ProductImageBasicDto> certification;
    private final CategoryInnerProductDetailDto category;

    public ProductDetailEnterpriseDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("brand") String brand,
            @JsonProperty("shortDescription") String shortDescription,
            @JsonProperty("description") String description,
            @JsonProperty("price") Double price,
            @JsonProperty("pointSale") Double pointSale,
            @JsonProperty("productStatus") ProductStatus productStatus,
            @JsonProperty("normal") List<ProductImageBasicDto> normal,
            @JsonProperty("certification") List<ProductImageBasicDto> certification,
            @JsonProperty("category") CategoryInnerProductDetailDto category) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.shortDescription = shortDescription;
        this.description = description;
        this.price = price;
        this.pointSale = pointSale;
        this.productStatus = productStatus;
        this.normal = normal;
        this.certification = certification;
        this.category = category;
    }

    @Data
    public static class CategoryInnerProductDetailDto implements Serializable {
        private final UUID categoryId;
        private final String categoryName;
        private final CategoryStatus status;

        @JsonCreator(mode = PROPERTIES)
        public CategoryInnerProductDetailDto(UUID categoryId, String categoryName, CategoryStatus status) {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.status = status;
        }
    }
}
