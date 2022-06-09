package com.springframework.csscapstone.payload.basic;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class PrizeImageDto {
    private final UUID id;
    private final String name;
    private final String path;
}
