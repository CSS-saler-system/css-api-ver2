package com.springframework.csscapstone.utils.mapper_utils;

import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.payload.response_dto.enterprise.CollaboratorResDto;
import com.springframework.csscapstone.payload.sharing.AccountUpdaterJsonDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapperForTest {
    MapperForTest INSTANCE = Mappers.getMapper(MapperForTest.class);

    AccountUpdaterJsonDto toAccountUpdaterPreJsonResDto(Account account);
    CollaboratorResDto toCollaboratorResDto(Account account);

}
