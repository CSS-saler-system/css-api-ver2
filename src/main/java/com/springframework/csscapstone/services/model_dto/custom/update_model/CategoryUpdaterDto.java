package com.springframework.csscapstone.services.model_dto.custom.update_model;

import com.springframework.csscapstone.data.status.CategoryStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryUpdaterDto {
    private UUID id;
    private String name;
    private CategoryStatus status;
}
