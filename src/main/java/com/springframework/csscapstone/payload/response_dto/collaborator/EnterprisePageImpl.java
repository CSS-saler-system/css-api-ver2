package com.springframework.csscapstone.payload.response_dto.collaborator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.payload.basic.EnterpriseDto;
import lombok.Data;

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
            @JsonProperty List<EnterpriseDto> data,
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
        this.first = first;
        this.last = last;
    }

}
