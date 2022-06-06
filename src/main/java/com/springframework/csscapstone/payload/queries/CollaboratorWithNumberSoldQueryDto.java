package com.springframework.csscapstone.payload.queries;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class CollaboratorWithNumberSoldQueryDto {
    private final UUID collaboratorId;
    private final UUID enterpriseId;
    private final Long sumQuantity;
    private final Long count;
}
