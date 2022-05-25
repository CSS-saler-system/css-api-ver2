package com.springframework.csscapstone.config.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.springframework.csscapstone.payload.request_dto.admin.ProductCreatorDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StringToUserConverter implements Converter<String, ProductCreatorDto> {

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public ProductCreatorDto convert(String value) {
        return objectMapper.readValue(value, ProductCreatorDto.class);
    }

}
