package com.springframework.csscapstone.payload.request_dto.admin;

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
