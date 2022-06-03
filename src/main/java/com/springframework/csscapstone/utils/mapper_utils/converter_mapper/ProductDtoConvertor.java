package com.springframework.csscapstone.utils.mapper_utils.converter_mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.payload.request_dto.enterprise.ProductUpdaterDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDtoConvertor implements Converter<String, ProductUpdaterDto> {

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public ProductUpdaterDto convert(String value) {
        return objectMapper.readValue(value, ProductUpdaterDto.class);
    }

}
