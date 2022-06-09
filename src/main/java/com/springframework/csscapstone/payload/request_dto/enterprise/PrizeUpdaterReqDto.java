package com.springframework.csscapstone.payload.request_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class PrizeUpdaterReqDto {
    private final String name;
    private final Double price;
    private final Long quantity;
    private final String description;
    private final AccountDto creator;

    @JsonCreator(mode = PROPERTIES)
    public PrizeUpdaterReqDto(
            @JsonProperty("name") String name,
            @JsonProperty("price") Double price,
            @JsonProperty("quantity") Long quantity,
            @JsonProperty("description") String description,
            @JsonProperty("creator") AccountDto creator) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.creator = creator;
    }
    @Data
    public static class AccountDto implements Serializable {
        private final UUID id;

        @JsonCreator(mode = PROPERTIES)
        public AccountDto(UUID id) {
            this.id = id;
        }
    }
}
