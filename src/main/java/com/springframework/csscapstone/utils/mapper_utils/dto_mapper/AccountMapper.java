package com.springframework.csscapstone.utils.mapper_utils.dto_mapper;

import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.EnterpriseLofiginTestResDto;
import com.springframework.csscapstone.payload.request_dto.admin.AccountCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.collaborator.AccountCollaboratorUpdaterDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.EnterpriseSignUpDto;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.EnterpriseResDto;
import com.springframework.csscapstone.payload.response_dto.moderator.AccountModeratorPageResDto;
import com.springframework.csscapstone.payload.sharing.AccountUpdaterJsonDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface AccountMapper {
    String DEFAULT_POINT = "0.0";
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "avatar", source = "avatar")
    @Mapping(target = "licenses", source = "license")
    @Mapping(target = "idCard", source = "idCard")
    AccountResDto toAccountResDto(Account entity);

    @Mapping(target = "dob", source = "dayOfBirth")
    @Mapping(target = "point", constant = DEFAULT_POINT)
    Account accountReqDtoToAccount(AccountCreatorReqDto accountCreatorReqDto);

    Account accountDtoToAccount(AccountUpdaterJsonDto accountDto);

    AccountUpdaterJsonDto accountToAccountUpdaterJsonDto(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    Account updateAccountFromAccountUpdaterJsonDto(
            AccountUpdaterJsonDto accountDto, @MappingTarget Account account);

    Account enterpriseSignUpDtoToAccount(EnterpriseSignUpDto enterpriseSignUpDto);

    EnterpriseSignUpDto accountToSignUpDto(Account account);

    EnterpriseSignUpDto accountToEnterpriseSignUpDto(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account updateAccountFromEnterpriseSignUpDto(EnterpriseSignUpDto enterpriseSignUpDto, @MappingTarget Account account);

    @Mapping(target = "avatar", source = "entity.avatar")
    @Mapping(target = "license", source = "entity.license")
    @Mapping(target = "idCard", source = "entity.idCard")
    @Mapping(target = "quantityCollaborator", source = "quantityCollab")
    EnterpriseResDto toEnterpriseResDto(Account entity, Long quantityCollab);

    @AfterMapping
    default void linkImages(@MappingTarget Account account) {
        account.getImages().forEach(image -> image.setAccount(account));
    }

    Account accountCollaboratorUpdaterDtoToAccount(AccountCollaboratorUpdaterDto accountCollaboratorUpdaterDto);

    AccountCollaboratorUpdaterDto accountToAccountCollaboratorUpdaterDto(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account updateAccountFromAccountCollaboratorUpdaterDto(AccountCollaboratorUpdaterDto accountCollaboratorUpdaterDto, @MappingTarget Account account);

    Account accountModeratorPageResDtoToAccount(AccountModeratorPageResDto accountModeratorPageResDto);

    AccountModeratorPageResDto accountToAccountModeratorPageResDto(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account updateAccountFromAccountModeratorPageResDto(AccountModeratorPageResDto accountModeratorPageResDto, @MappingTarget Account account);
}
