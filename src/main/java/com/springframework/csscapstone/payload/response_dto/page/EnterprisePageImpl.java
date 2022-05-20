package com.springframework.csscapstone.payload.response_dto.page;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.springframework.csscapstone.data.domain.EnterpriseDto;
import com.springframework.csscapstone.payload.basic.AccountDto;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class EnterprisePageImpl  implements Serializable {
    private final List<EnterpriseDto> data;
    private final int number;
    private final int size;
    private final long totalElement;
    private final boolean first;
    private final boolean last;

    @JsonCreator(mode = PROPERTIES)
    public EnterprisePageImpl(
            @JsonProperty("data") List<EnterpriseDto> data,
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
        this.first = first;
        this.last = last;
    }

}
