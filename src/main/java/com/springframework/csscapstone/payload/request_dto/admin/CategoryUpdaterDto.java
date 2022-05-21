package com.springframework.csscapstone.payload.request_dto.admin;

import com.springframework.csscapstone.data.status.CategoryStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryUpdaterDto {
    private UUID id;
    private String name;
    private CategoryStatus status;
}
