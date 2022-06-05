package com.springframework.csscapstone.payload.basic;

import com.springframework.csscapstone.data.status.PrizeStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class PrizeBasicDto implements Serializable {
    private final UUID id;
    private final String name;
    private final Double price;
    private final Long quantity;
    private final String description;
    private final PrizeStatus statusPrize;
}
