package com.springframework.csscapstone.css_business.model_dto.custom.search_model;

import com.springframework.csscapstone.css_data.status.CategoryStatus;
import lombok.Data;

@Data
public class CategorySearchDto {
    private final String categoryName;
    private final CategoryStatus status;

}
