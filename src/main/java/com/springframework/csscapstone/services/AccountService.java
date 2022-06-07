package com.springframework.csscapstone.services;


import com.springframework.csscapstone.payload.request_dto.admin.AccountCreatorReqDto;
import com.springframework.csscapstone.payload.response_dto.PageEnterpriseResDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.CollaboratorResDto;
import com.springframework.csscapstone.payload.sharing.AccountUpdaterJsonDto;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountExistException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    PageImplResDto<AccountResDto> getAccountDto(
            String name, String phone,
            String email, String address,
            Integer pageSize, Integer pageNumber);

    AccountResDto getById(UUID id) throws AccountInvalidException, AccountNotFoundException;

    UUID createAccount(
            AccountCreatorReqDto dto,
            MultipartFile avatar,
            MultipartFile license,
            MultipartFile idCards) throws AccountExistException, AccountNotFoundException;

    UUID updateAccount(AccountUpdaterJsonDto accountUpdaterJsonDto,
                       MultipartFile avatars,
                       MultipartFile licenses,
                       MultipartFile idCards) throws AccountInvalidException;

    void disableAccount(UUID id);

    PageEnterpriseResDto getAllHavingEnterpriseRole(Integer pageNumber, Integer pageSize);

    PageImplResDto<AccountResDto> getAllCollaboratorsOfEnterprise(
            UUID idEnterprise, Integer pageNumber, Integer pageSize);

    //    UUID createAccount(AccountDto dto);
    PageImplResDto<CollaboratorResDto> collaboratorsOfEnterpriseIncludeNumberOfOrder(
            UUID idEnterprise, Integer pageNumber, Integer pageSize);

    List<CollaboratorResDto> collaboratorMappingCampaign(UUID campaign);

}
