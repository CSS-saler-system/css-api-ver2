package com.springframework.csscapstone.utils.mapper_utils.converter_mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframework.csscapstone.payload.response_dto.collaborator.CustomerResDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountUpdateConvertor implements Converter<String, CustomerResDto.AccountUpdaterInnerCustomerResDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public CustomerResDto.AccountUpdaterInnerCustomerResDto convert(String value) {
        return objectMapper.readValue(value, CustomerResDto.AccountUpdaterInnerCustomerResDto.class);
    }

}
