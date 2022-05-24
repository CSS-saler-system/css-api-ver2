package com.springframework.csscapstone.payload.response_dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.payload.basic.ProductDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class PageProductDto implements Serializable {
    static final long serialVersionUID = 1114715135625836949L;
    private final List<ProductDto> data;
    private final int number;
    private final int size;
    private final long totalElement;
    private final long totalPage;
    private final boolean first;
    private final boolean last;

    @JsonCreator(mode = PROPERTIES)
    public PageProductDto(
            @JsonProperty List<ProductDto> data,
            @JsonProperty int number,
            @JsonProperty int size,
            @JsonProperty long totalElements,
            @JsonProperty int totalPages,
            @JsonProperty boolean first,
            @JsonProperty boolean last
    ) {
        this.data = data;
        this.number = number;
        this.size = size;
        this.totalElement = totalElements;
        this.totalPage = totalPages;
        this.first = first;
        this.last = last;
    }
}
