package com.springframework.csscapstone.payload.response_dto.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.domain.Category;
import com.springframework.csscapstone.data.status.CategoryStatus;
import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.request_dto.admin.ProductImageReqDto;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Data
public class CategoryResDto implements Serializable {
    private final UUID id;
    private final String categoryName;
    private final CategoryStatus status;
    private final List<ProductInnerCategoryResDto> products;

    public CategoryResDto() {
        System.out.println("This is called");
        this.id = null;
        this.categoryName = "";
        this.products = Collections.emptyList();
        this.status = CategoryStatus.DISABLED;
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CategoryResDto(@JsonProperty("id") UUID id,
                          @JsonProperty("categoryName") String categoryName,
                          @JsonProperty("status") CategoryStatus status,
                          @JsonProperty("products") List<ProductInnerCategoryResDto> products) {
        this.id = id;
        this.categoryName = categoryName;
        this.status = status;
        this.products = products;
    }

    @Data
    public static class ProductInnerCategoryResDto implements Serializable {
        private final UUID id;
        private final String name;
        private final String brand;
        private final String shortDescription;
        private final String description;
        private final Double price;
        private final Double pointSale;
        private final ProductStatus productStatus;
        private final List<ProductImageReqDto> image;

        public ProductInnerCategoryResDto(
                @JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("brand") String brand,
                @JsonProperty("shortDescription") String shortDescription,
                @JsonProperty("description") String description,
                @JsonProperty("price") Double price,
                @JsonProperty("pointSale") Double pointSale,
                @JsonProperty("productStatus") ProductStatus productStatus,
                @JsonProperty("image") List<ProductImageReqDto> image) {
            this.id = id;
            this.name = name;
            this.brand = brand;
            this.shortDescription = shortDescription;
            this.description = description;
            this.price = price;
            this.pointSale = pointSale;
            this.productStatus = productStatus;
            this.image = image;
        }
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CategoryResDto(Category category, List<ProductInnerCategoryResDto> products) {
        this.id = category.getId();
        this.categoryName = category.getCategoryName();
        this.status = category.getStatus();
        this.products = products;
    }
}
