package com.springframework.csscapstone.payload.basic;

import com.springframework.csscapstone.data.status.CategoryStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class CategoryDto implements Serializable {
    private final UUID id;
    private final String categoryName;
    private final CategoryStatus status;
}
