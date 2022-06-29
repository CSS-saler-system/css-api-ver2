package com.springframework.csscapstone.payload.basic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class CategoryBasicDto implements Serializable {
    private final UUID categoryId;
    private final String categoryName;


    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CategoryBasicDto(
            @JsonProperty("categoryId") UUID categoryId,
            @JsonProperty("categoryName") String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
