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

//    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
//    public QueriesProductDto(
//            @JsonProperty("product") Product product,
//            @JsonProperty("sum_quantity") Long sumQuantity) {
//        this.product = product;
//        this.sumQuantity = sumQuantity;
//    }
////
//    @Data
//    public static class ProductBufferDto {
//        private final UUID id;
//        private final String name;
//        private final String shortDescription;
//        private final String description;
//        private final Long quantityInStock;
//        private final Double price;
//        private final Double pointSale;
//        private final ProductStatus productStatus;
//        private final List<ProductImageDto> images;
//
//        public ProductBufferDto(
//                @JsonProperty("id") UUID id,
//                @JsonProperty("name") String name,
//                @JsonProperty("short_description") String shortDescription,
//                @JsonProperty("description") String description,
//
//                @JsonProperty("quantity_in_stock") Long quantityInStock,
//
//                @JsonProperty("price") Double price,
//
//                @JsonProperty("point_sale") Double pointSale,
//                @JsonProperty("status") ProductStatus productStatus,
//                @JsonProperty("images") List<ProductImageDto> images) {
//            this.id = id;
//            this.name = name;
//            this.shortDescription = shortDescription;
//            this.quantityInStock = quantityInStock;
//            this.description = description;
//            this.price = price;
//            this.pointSale = pointSale;
//            this.productStatus = productStatus;
//            this.images = images;
//        }
//    }


}
