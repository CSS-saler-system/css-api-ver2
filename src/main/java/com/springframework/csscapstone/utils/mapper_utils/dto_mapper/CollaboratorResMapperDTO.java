package com.springframework.csscapstone.utils.mapper_utils.dto_mapper;

import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.payload.response_dto.enterprise.CollaboratorResDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CollaboratorResMapperDTO {
    CollaboratorResMapperDTO INSTANCE = Mappers.getMapper(CollaboratorResMapperDTO.class);

    @Mapping(target = "id", source = "account.id")
    @Mapping(target = "name", source = "account.name")
    @Mapping(target = "dob", source = "account.dob")
    @Mapping(target = "phone", source = "account.phone")
    @Mapping(target = "address", source = "account.address")
    @Mapping(target = "description", source = "account.description")
    @Mapping(target = "gender", source = "account.gender")
    @Mapping(target = "point", source = "account.point")
    @Mapping(target = "imageBasicDto", source = "account.images")
    @Mapping(target = "totalQuantity", source = "totalQuantity")
    CollaboratorResDto toCollaboratorResDto(Account account, Long totalQuantity);

}
