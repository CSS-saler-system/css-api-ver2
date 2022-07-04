package com.springframework.csscapstone.utils.mapper_utils.converter_mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.payload.request_dto.admin.ProductCreatorReqDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProductCreatorConvertor implements Converter<String, ProductCreatorReqDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public ProductCreatorReqDto convert(String value) {
        if (Objects.isNull(value)) throw new RuntimeException("Cant parse Json:" + value + " to Object");
        return objectMapper.readValue(value, ProductCreatorReqDto.class);
    }

}
