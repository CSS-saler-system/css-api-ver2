package com.springframework.csscapstone.css_business.model_dto.custom;

import com.springframework.csscapstone.css_data.status.ProductImageType;
import lombok.Data;

import java.io.Serializable;

@Data
public class Image implements Serializable {
    private final Long id;
    private final ProductImageType type;
    private final String path;
}