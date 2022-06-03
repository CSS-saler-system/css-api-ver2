package com.springframework.csscapstone.payload.queries;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.domain.Product;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResponseDto;
import lombok.Data;

import java.util.UUID;

@Data
public class QueriesProductDto {
//    private final _ProductResponseDto product;
    private final Product product;
    private final Long sumQuantity;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public QueriesProductDto(Product product, Long sumQuantity) {
        this.product = product;
        this.sumQuantity = sumQuantity;
    }

//
//
//    @Data
//    static class _ProductResponseDto {
//        private final UUID id;
//        private final String name;
//        private final String shortDescription;
//
//        @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
//
//        public _ProductResponseDto(
//                @JsonProperty("id") UUID id,
//                @JsonProperty("name") String name,
//                @JsonProperty("short_description") String shortDescription) {
//            this.id = id;
//            this.name = name;
//            this.shortDescription = shortDescription;
//        }
//    }

}
