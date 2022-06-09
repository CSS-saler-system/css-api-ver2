package com.springframework.csscapstone.services;

import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.PrizeResDto;

public interface PrizeService {
    PageImplResDto<PrizeResDto> getAll(Integer pageNumber, Integer pageSize);
}
