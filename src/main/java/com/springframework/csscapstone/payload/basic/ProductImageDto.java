package com.springframework.csscapstone.payload.basic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.status.ProductImageType;
import lombok.Data;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class ProductImageDto implements Serializable {
    private final Long id;
    private final ProductImageType type;
    private final String path;

    @JsonCreator(mode = PROPERTIES)
    public ProductImageDto(
            @JsonProperty("id") Long id,
            @JsonProperty("type") ProductImageType type,
            @JsonProperty("path") String path) {
        this.id = id;
        this.type = type;
        this.path = path;
    }
}
