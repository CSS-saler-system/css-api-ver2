package com.springframework.csscapstone.utils.mapper_utils.converter_mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.payload.request_dto.admin.ProductCreatorDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductCreatorConvertor implements Converter<String, ProductCreatorDto> {

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public ProductCreatorDto convert(String value) {
        return objectMapper.readValue(value, ProductCreatorDto.class);
    }

}
