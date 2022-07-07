package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.status.PrizeStatus;
import com.springframework.csscapstone.payload.basic.PrizeImageDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class PrizeResDto {
    private final UUID id;
    private final String name;
    private final Double price;
    @JsonCreator(mode = PROPERTIES)
    public PrizeResDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("price") Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
