package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.status.ProductImageType;
import com.springframework.csscapstone.data.status.ProductStatus;
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
    private final Long quantity;
    private final Double price;
    private final Double pointSale;
    private final ProductStatus productStatus;
    private final List<CertificationImage> image;
    private final CategoryDto category;
    private final AccountDto account;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProductResDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("brand") String brand,
            @JsonProperty("short_description") String shortDescription,
            @JsonProperty("description") String description,
            @JsonProperty("quantity") Long quantity,
            @JsonProperty("price") Double price,
            @JsonProperty("point_sale") Double pointSale,
            @JsonProperty("product_status") ProductStatus productStatus,
            @JsonProperty("certification_images") List<CertificationImage> image,
            @JsonProperty("category") CategoryDto category,
            @JsonProperty("account") AccountDto account) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.shortDescription = shortDescription;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.pointSale = pointSale;
        this.productStatus = productStatus;
        this.image = image;
        this.category = category;
        this.account = account;
    }


    @Data
    public static class ProductImageDto implements Serializable {
        private final Long id;
        private final ProductImageType type;
        private final String path;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public ProductImageDto(
                @JsonProperty("id") Long id,
                @JsonProperty("type") ProductImageType type,
                @JsonProperty("path") String path) {
            this.id = id;
            this.type = type;
            this.path = path;
        }
    }

    @Data
    public static class CertificationImage implements Serializable {
        private final Long id;
        private final ProductImageType type;
        private final String path;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public CertificationImage(
                @JsonProperty("id") Long id,
                @JsonProperty("type") ProductImageType type,
                @JsonProperty("path") String path) {
            this.id = id;
            this.type = type;
            this.path = path;
        }
    }

    @Data
    public static class CategoryDto implements Serializable {
        private final UUID id;
        private final String categoryName;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public CategoryDto(@JsonProperty("id") UUID id, @JsonProperty("category_name") String categoryName) {
            this.id = id;
            this.categoryName = categoryName;
        }
    }

    @Data
    public static class AccountDto implements Serializable {
        private final UUID id;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public AccountDto(@JsonProperty("id") UUID id) {
            this.id = id;
        }
    }
}
