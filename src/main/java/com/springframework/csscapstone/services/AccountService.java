package com.springframework.csscapstone.services;


import com.springframework.csscapstone.payload.request_dto.admin.AccountCreatorDto;
import com.springframework.csscapstone.payload.response_dto.PageEnterpriseDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResponse;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResponseDto;
import com.springframework.csscapstone.payload.sharing.AccountUpdaterDto;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountExistException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import java.util.UUID;

public interface AccountService {
    PageImplResponse<AccountResponseDto> getAccountDto(
            String name, String phone,
            String email, String address, Integer pageSize, Integer pageNumber);

    AccountResponseDto getById(UUID id) throws AccountInvalidException, AccountNotFoundException;

    UUID createAccount(
            AccountCreatorDto dto,
            MultipartFile avatar,
            MultipartFile license,
            MultipartFile idCards) throws AccountExistException, AccountNotFoundException;

    UUID updateAccount(AccountUpdaterDto accountUpdaterDto,
                       MultipartFile avatars,
                       MultipartFile licenses,
                       MultipartFile idCards) throws AccountInvalidException;

    void disableAccount(UUID id);

    PageEnterpriseDto getAllHavingEnterpriseRole(Integer pageNumber, Integer pageSize);

//    UUID createAccount(AccountDto dto);

}
