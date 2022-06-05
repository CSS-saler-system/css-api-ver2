package com.springframework.csscapstone.payload.queries;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.domain.Product;
import lombok.Data;

@Data
public class QueriesProductDto {
    //    private final _ProductResponseDto product;
    private final Product product;
    private final Long sumQuantity;

}
