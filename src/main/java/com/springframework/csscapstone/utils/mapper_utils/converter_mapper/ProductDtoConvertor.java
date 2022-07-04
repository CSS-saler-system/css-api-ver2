package com.springframework.csscapstone.utils.mapper_utils.converter_mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.payload.request_dto.enterprise.ProductUpdaterReqDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDtoConvertor implements Converter<String, ProductUpdaterReqDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public ProductUpdaterReqDto convert(String value) {
        return objectMapper.readValue(value, ProductUpdaterReqDto.class);
    }

}
