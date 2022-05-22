package com.springframework.csscapstone.payload.response_dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.domain.EnterpriseDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;
@Data
public class PageImplResponse<T> implements Serializable {
    static final long serialVersionUID = 1114715135625836949L;
    private final List<T> data;
    private final int number;
    private final int size;
    private final long totalElement;
    private final long totalPage;
    private final boolean first;
    private final boolean last;

    @JsonCreator(mode = PROPERTIES)
    public PageImplResponse(
            @JsonProperty("data") List<T> data,
            @JsonProperty("page_number") int number,
            @JsonProperty("page_size") int size,
            @JsonProperty("total_elements") long totalElements,
            @JsonProperty("totalPages") int totalPages,
            @JsonProperty("first_page") boolean first,
            @JsonProperty("last_page") boolean last
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
