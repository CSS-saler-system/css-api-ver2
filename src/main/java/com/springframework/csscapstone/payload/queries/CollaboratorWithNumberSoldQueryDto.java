package com.springframework.csscapstone.payload.queries;

import lombok.Data;

import java.util.UUID;

@Data
public class CollaboratorWithNumberSoldQueryDto {
    private final UUID collaboratorId;
    private final Long sumQuantity;
    private final Long count;
}
