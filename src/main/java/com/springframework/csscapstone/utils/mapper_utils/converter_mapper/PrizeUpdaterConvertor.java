package com.springframework.csscapstone.utils.mapper_utils.converter_mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.payload.request_dto.enterprise.PrizeUpdaterReqDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PrizeUpdaterConvertor implements Converter<String, PrizeUpdaterReqDto> {
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public PrizeUpdaterReqDto convert(String source) {
        return objectMapper.readValue(source, PrizeUpdaterReqDto.class);
    }
}
