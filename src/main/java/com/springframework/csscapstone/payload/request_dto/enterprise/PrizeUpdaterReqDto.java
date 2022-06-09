package com.springframework.csscapstone.payload.request_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.status.PrizeStatus;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class PrizeUpdaterReqDto {
    @NotNull(message = "The id must be not empty")
    private final UUID id;
    private final String name;
    @NotNull(message = "The price must be not null")
    @Min(value = 1, message = "The quantity must be greater than 1.0")
    private final Double price;
    @Min(value = 1, message = "The quantity must be greater than 1")
    private final Long quantity;
    private final String description;

    private final PrizeStatus status;

    @JsonCreator(mode = PROPERTIES)
    public PrizeUpdaterReqDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("price") Double price,
            @JsonProperty("quantity") Long quantity,
            @JsonProperty("description") String description,
            @JsonProperty("status") PrizeStatus status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.status = status;
    }

}
