package com.springframework.csscapstone.payload.request_dto.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ProductImageDto implements Serializable {
    @NotNull(message = "The id product must be not null")
    private final Long id;
    @NotEmpty(message = "The type product must be not null")
    private final String path;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProductImageDto(
            @JsonProperty Long id,
            @JsonProperty String path) {
        this.id = id;
        this.path = path;
    }
}
