package com.springframework.csscapstone.services.model_dto.basic;

import com.springframework.csscapstone.data.status.ProductImageType;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductImageDto implements Serializable {
    private final Long id;
    private final ProductImageType type;
    private final String path;
}
