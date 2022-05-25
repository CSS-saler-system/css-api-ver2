package com.springframework.csscapstone.services;


import com.springframework.csscapstone.payload.basic.AccountDto;
import com.springframework.csscapstone.payload.request_dto.admin.AccountCreatorDto;
import com.springframework.csscapstone.payload.request_dto.admin.AccountUpdaterDto;
import com.springframework.csscapstone.payload.response_dto.PageAccountDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.EnterpriseResponseDto;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountExistException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    PageAccountDto getAllDto(
            String name, String phone,
            String email, String address, Integer pageSize, Integer pageNumber);

    AccountDto getById(UUID id) throws AccountInvalidException;

    UUID createAccount(AccountCreatorDto dto) throws AccountExistException, AccountNotFoundException;

    UUID updateAccount(AccountUpdaterDto dto) throws AccountInvalidException;

    void disableAccount(UUID id);

    List<EnterpriseResponseDto> getAllHavingEnterpriseRole();

//    UUID createAccount(AccountDto dto);

}
