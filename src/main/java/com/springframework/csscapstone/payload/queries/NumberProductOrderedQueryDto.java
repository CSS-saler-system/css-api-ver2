package com.springframework.csscapstone.payload.queries;

import com.springframework.csscapstone.data.domain.Product;
import lombok.Data;

@Data
public class NumberProductOrderedQueryDto {
    //    private final _ProductResponseDto product;
    private final Product product;
    private final Long sumQuantity;

}
