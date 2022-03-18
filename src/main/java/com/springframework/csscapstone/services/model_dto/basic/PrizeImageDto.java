package com.springframework.csscapstone.services.model_dto.basic;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class PrizeImageDto implements Serializable {
    private final UUID id;
    private final String name;
    private final String path;
}
