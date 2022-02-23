package com.springframework.csscapstone.css_business.model_dto.basic;

import com.springframework.csscapstone.css_data.status.PrizeStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class PrizeDto implements Serializable {
    private final UUID id;
    private final String name;
    private final Double price;
    private final Long quantity;
    private final String description;
    private final PrizeStatus statusPrize;
}
