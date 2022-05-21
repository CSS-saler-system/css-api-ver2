package com.springframework.csscapstone.payload.request_dto.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.status.ProductImageType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
public class ProductImageDto implements Serializable {
    @NotNull(message = "The id product must be not null")
    private final Long id;
    @NotNull(message = "The type product must be not null")
    private final ProductImageType type;
    @NotEmpty(message = "The type product must be not null")
    private final String path;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProductImageDto(
            @JsonProperty("id") Long id,
            @JsonProperty("image_product") ProductImageType type,
            @JsonProperty("path") String path) {
        this.id = id;
        this.type = type;
        this.path = path;
    }
}
