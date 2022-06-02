package com.springframework.csscapstone.config.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.payload.request_dto.admin.ProductCreatorDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.validation.constraints.NotNull;
import java.util.Objects;

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
