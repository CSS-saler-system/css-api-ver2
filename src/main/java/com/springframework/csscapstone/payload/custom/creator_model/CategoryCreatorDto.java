package com.springframework.csscapstone.payload.custom.creator_model;

import com.springframework.csscapstone.data.status.CategoryStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CategoryCreatorDto {
    @NotNull
    private final UUID accountId;
    @NotNull
    private final String categoryName;
    private CategoryStatus status = CategoryStatus.ACTIVE;
}
