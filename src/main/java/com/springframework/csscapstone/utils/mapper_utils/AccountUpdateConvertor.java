package com.springframework.csscapstone.utils.mapper_utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.payload.response_dto.collaborator.CustomerResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountUpdateConvertor implements Converter<String, CustomerResponseDto.AccountUpdaterDto> {

    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public CustomerResponseDto.AccountUpdaterDto convert(String value) {
        return objectMapper.readValue(value, CustomerResponseDto.AccountUpdaterDto.class);
    }

}
