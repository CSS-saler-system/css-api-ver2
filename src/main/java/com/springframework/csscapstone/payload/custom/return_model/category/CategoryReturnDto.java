package com.springframework.csscapstone.payload.custom.return_model.category;

import com.springframework.csscapstone.data.status.CategoryStatus;
import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.payload.custom.Image;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class CategoryReturnDto implements Serializable {
    private final UUID id;
    private final String categoryName;
    private final CategoryStatus status;
    private final List<_ProductDto> products;

    @Data
    public static class _ProductDto implements Serializable {
        private final UUID id;
        private final String name;
        private final String brand;
        private final Double weight;
        private final String shortDescription;
        private final String description;
        private final Long quantityInStock;
        private final Double price;
        private final Double pointSale;
        private final ProductStatus productStatus;
        private final List<Image> image;
        private final List<Image> certificateImageProduct;

    }
}