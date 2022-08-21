package com.springframework.csscapstone.data.domain;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AccountImageMapper {
    AccountImageMapper INSTANCE = Mappers.getMapper(AccountImageMapper.class);
    AccountImage accountImageBasicVer2DtoToAccountImage(AccountImageBasicVer2Dto accountImageBasicVer2Dto);

    AccountImageBasicVer2Dto accountImageToAccountImageBasicVer2Dto(AccountImage accountImage);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AccountImage updateAccountImageFromAccountImageBasicVer2Dto(AccountImageBasicVer2Dto accountImageBasicVer2Dto, @MappingTarget AccountImage accountImage);
}
