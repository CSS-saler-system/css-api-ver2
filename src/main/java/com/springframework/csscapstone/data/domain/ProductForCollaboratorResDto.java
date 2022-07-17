package com.springframework.csscapstone.data.domain;

import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.data.status.RequestStatus;
import com.springframework.csscapstone.payload.basic.CategoryBasicDto;
import com.springframework.csscapstone.payload.basic.ProductImageBasicDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class ProductForCollaboratorResDto implements Serializable {
    private final UUID id;
    private final String name;
    private final String brand;
    private final String shortDescription;
    private final String description;
    private final Double price;
    private final Double pointSale;
    private final ProductStatus productStatus;
    private final List<ProductImageBasicDto> image;
    private final CategoryBasicDto category;

}
