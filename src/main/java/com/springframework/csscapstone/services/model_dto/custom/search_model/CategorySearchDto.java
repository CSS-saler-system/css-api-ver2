package com.springframework.csscapstone.services.model_dto.custom.search_model;

import com.springframework.csscapstone.data.status.CategoryStatus;
import lombok.Data;

@Data
public class CategorySearchDto {
    private final String categoryName;
    private final CategoryStatus status;

}
