package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResDto;

import java.util.List;
import java.util.UUID;

public interface AccountRedisCacheable {
    AccountResDto getAccountResDtoByID(UUID accountId);
    void saveOrganization(AccountResDto account);
    void deleteAccountById(UUID accountId);
    List<AccountResDto> getAllAccountResDtoFromCache();

}
