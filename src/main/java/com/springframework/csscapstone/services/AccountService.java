package com.springframework.csscapstone.services;


import com.springframework.csscapstone.payload.basic.AccountDto;
import com.springframework.csscapstone.payload.request_dto.admin.AccountRegisterDto;
import com.springframework.csscapstone.payload.request_dto.admin.AccountUpdaterDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.EnterpriseResponseDto;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountExistException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    List<AccountDto> getAllDto(String name, String phone,
                               String email, String address, String description, boolean status);

    AccountDto getById(UUID id) throws AccountInvalidException;

    UUID registerAccount(AccountRegisterDto dto) throws AccountExistException;

    UUID updateAccount(AccountUpdaterDto dto) throws AccountInvalidException;

    void disableAccount(UUID id);

    List<EnterpriseResponseDto> getAllHavingEnterpriseRole();

//    UUID createAccount(AccountDto dto);

}
