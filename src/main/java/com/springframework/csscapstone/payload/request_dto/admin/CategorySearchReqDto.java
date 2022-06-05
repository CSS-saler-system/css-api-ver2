package com.springframework.csscapstone.payload.request_dto.admin;

import com.springframework.csscapstone.data.status.CategoryStatus;
import lombok.Data;

@Data
public class CategorySearchReqDto {
    private final String categoryName;
    private final CategoryStatus status;

}
