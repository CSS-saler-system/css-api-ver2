package com.springframework.csscapstone.payload.response_dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.status.CategoryStatus;
import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.request_dto.admin.ProductImageReqDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class CategoryResDto implements Serializable {
    private final UUID id;
    private final String categoryName;
    private final CategoryStatus status;
    private final List<_ProductDto> products;

    public CategoryResDto(@JsonProperty("id") UUID id,
                          @JsonProperty("category_name") String categoryName,
                          @JsonProperty("status") CategoryStatus status,
                          @JsonProperty("products") List<_ProductDto> products) {
        this.id = id;
        this.categoryName = categoryName;
        this.status = status;
        this.products = products;
    }

    @Data
    public static class _ProductDto implements Serializable {
        private final UUID id;
        private final String name;
        private final String brand;
        private final String shortDescription;
        private final String description;
        private final Long quantityInStock;
        private final Double price;
        private final Double pointSale;
        private final ProductStatus productStatus;
        private final List<ProductImageReqDto> image;

        public _ProductDto(@JsonProperty("id") UUID id,
                           @JsonProperty("name") String name,
                           @JsonProperty("brand") String brand,
                           @JsonProperty("short_description") String shortDescription,
                           @JsonProperty("description") String description,
                           @JsonProperty("quantity") Long quantityInStock,
                           @JsonProperty("price") Double price,
                           @JsonProperty("point_sale") Double pointSale,
                           @JsonProperty("product_status") ProductStatus productStatus,
                           @JsonProperty("images") List<ProductImageReqDto> image) {
            this.id = id;
            this.name = name;
            this.brand = brand;
            this.shortDescription = shortDescription;
            this.description = description;
            this.quantityInStock = quantityInStock;
            this.price = price;
            this.pointSale = pointSale;
            this.productStatus = productStatus;
            this.image = image;
        }
    }
}
