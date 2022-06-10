package com.springframework.csscapstone.payload.response_dto.collaborator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class OrderDetailResDto implements Serializable {
    private final UUID id;
    private final String nameProduct;
    private final Long quantity;
    private final Double totalPointProduct;
    private final Double totalPriceProduct;

    @JsonCreator(mode = PROPERTIES)
    public OrderDetailResDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name_prodct") String nameProduct,
            @JsonProperty("quantity") Long quantity,
            @JsonProperty("total_point") Double totalPointProduct,
            @JsonProperty("total_price") Double totalPriceProduct) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.quantity = quantity;
        this.totalPointProduct = totalPointProduct;
        this.totalPriceProduct = totalPriceProduct;
    }
}
