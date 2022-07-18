package com.springframework.csscapstone.payload.response_dto.collaborator;

import com.springframework.csscapstone.payload.basic.ProductImageBasicDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class ProductForCollabGetDetailResDto implements Serializable {
    private final UUID id;
    private final String name;
    private final String brand;
    private final String shortDescription;
    private final String description;
    private final Double price;
    private final Double pointSale;
    private final List<ProductImageBasicDto> image;
    private final CategoryDto category;

    @Data
    public static class CategoryDto implements Serializable {
        private final UUID id;
        private final String categoryName;
    }
}
