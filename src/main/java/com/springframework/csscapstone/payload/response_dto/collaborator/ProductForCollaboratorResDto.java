package com.springframework.csscapstone.payload.response_dto.collaborator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.status.ProductStatus;
import com.springframework.csscapstone.data.status.RequestStatus;
import com.springframework.csscapstone.payload.basic.CategoryBasicDto;
import com.springframework.csscapstone.payload.basic.ProductImageBasicDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class ProductForCollaboratorResDto implements Serializable {
    private final UUID id;
    private final String name;
    private final String brand;
    private final String shortDescription;
    private final String description;
    private final Double price;
    private final Double pointSale;
    private final ProductStatus productStatus;
    private final List<ProductImageBasicDto> image;
    private final CategoryBasicDto category;
    private final RequestSellingProductInnerResDto request;
    public ProductForCollaboratorResDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("brand") String brand,
            @JsonProperty("shortDescription") String shortDescription,
            @JsonProperty("description") String description,
            @JsonProperty("price") Double price,
            @JsonProperty("pointSale") Double pointSale,
            @JsonProperty("productStatus") ProductStatus productStatus,
            @JsonProperty("image") List<ProductImageBasicDto> image,
            @JsonProperty("category") CategoryBasicDto category,
            @JsonProperty("request") RequestSellingProductInnerResDto request) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.shortDescription = shortDescription;
        this.description = description;
        this.price = price;
        this.pointSale = pointSale;
        this.productStatus = productStatus;
        this.image = image;
        this.category = category;
        this.request = request;
    }

    @Data
    public static class RequestSellingProductInnerResDto {
        private final RequestStatus requestStatus;

        @JsonCreator(mode = PROPERTIES)
        public RequestSellingProductInnerResDto(RequestStatus requestStatus) {
            this.requestStatus = requestStatus;
        }
    }
}
