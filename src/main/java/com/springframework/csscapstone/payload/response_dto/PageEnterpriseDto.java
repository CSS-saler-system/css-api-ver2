package com.springframework.csscapstone.payload.response_dto;

import com.springframework.csscapstone.payload.response_dto.collaborator.EnterpriseResponseDto;
import lombok.Data;

import java.util.List;

public class PageEnterpriseDto extends PageImplResponse<EnterpriseResponseDto> {
    public PageEnterpriseDto(List<EnterpriseResponseDto> data, int number, int size, long totalElements, int totalPages, boolean first, boolean last) {
        super(data, number, size, totalElements, totalPages, first, last);
    }
}
