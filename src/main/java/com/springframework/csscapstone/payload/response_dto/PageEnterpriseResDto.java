package com.springframework.csscapstone.payload.response_dto;

import com.springframework.csscapstone.payload.response_dto.collaborator.EnterpriseResDto;

import java.util.List;

public class PageEnterpriseResDto extends PageImplResDto<EnterpriseResDto> {
    public PageEnterpriseResDto(List<EnterpriseResDto> data, int number, int size, long totalElements, int totalPages, boolean first, boolean last) {
        super(data, number, size, totalElements, totalPages, first, last);
    }
}
