package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.data.repositories.PrizeRepository;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.PrizeResDto;
import com.springframework.csscapstone.services.PrizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrizeServiceImpl implements PrizeService {

    private final PrizeRepository prizeRepository;

    @Override
    public PageImplResDto<PrizeResDto> getAll(Integer pageNumber, Integer pageSize) {
        return null;
    }
}
