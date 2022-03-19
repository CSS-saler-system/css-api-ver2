package com.springframework.csscapstone.services.business;


import com.springframework.csscapstone.services.model_dto.basic.AccountDto;
import com.springframework.csscapstone.services.model_dto.custom.creator_model.AccountRegisterDto;
import com.springframework.csscapstone.services.model_dto.custom.update_model.AccountUpdaterDto;
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

//    UUID createAccount(AccountDto dto);

}
