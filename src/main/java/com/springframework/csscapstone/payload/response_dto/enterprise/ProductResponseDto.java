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
public class ProductResponseDto implements Serializable {
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
    public ProductResponseDto(
            @JsonProperty UUID id,
            @JsonProperty String name,
            @JsonProperty String brand,
            @JsonProperty String shortDescription,
            @JsonProperty String description,
            @JsonProperty Long quantity,
            @JsonProperty Double price,
            @JsonProperty Double pointSale,
            @JsonProperty ProductStatus productStatus,
            @JsonProperty List<CertificationImage> image,
            @JsonProperty CategoryDto category,
            @JsonProperty AccountDto account) {
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
                @JsonProperty Long id,
                @JsonProperty ProductImageType type,
                @JsonProperty String path) {
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
                @JsonProperty Long id,
                @JsonProperty ProductImageType type,
                @JsonProperty String path) {
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
        public CategoryDto(@JsonProperty UUID id, @JsonProperty String categoryName) {
            this.id = id;
            this.categoryName = categoryName;
        }
    }

    @Data
    public static class AccountDto implements Serializable {
        private final UUID id;

        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
        public AccountDto(UUID id) {
            this.id = id;
        }
    }
}
