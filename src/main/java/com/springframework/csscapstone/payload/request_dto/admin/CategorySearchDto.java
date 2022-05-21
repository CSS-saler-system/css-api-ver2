package com.springframework.csscapstone.payload.request_dto.admin;

import com.springframework.csscapstone.data.status.CategoryStatus;
import lombok.Data;

@Data
public class CategorySearchDto {
    private final String categoryName;
    private final CategoryStatus status;

}
