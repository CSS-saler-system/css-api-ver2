package com.springframework.csscapstone.data.repositories.impl;

import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.repositories.AccountRedisCacheable;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AccountRedisCacheableImpl implements AccountRedisCacheable {
    private final RedisTemplate<String, Account> redisTemplate;
    private static final String ACCOUNT = "ACCOUNT";
    private HashOperations<String, Object, Object> accountHashOperations;

    @PostConstruct
    private void init() {
        accountHashOperations = redisTemplate.opsForHash();
    }

    @Override
    public AccountResDto getAccountResDtoByID(UUID accountId) {
        return (AccountResDto) accountHashOperations.get(ACCOUNT, accountId);
    }

    @Override
    public void saveOrganization(AccountResDto account) {
        this.accountHashOperations.put(ACCOUNT, account.getId(), account);
    }

    @Override
    public void deleteAccountById(UUID accountId) {
        this.accountHashOperations.delete(ACCOUNT, accountId);
    }

    @Override
    public List<AccountResDto> getAllAccountResDtoFromCache() {
        return this.accountHashOperations.entries(ACCOUNT).values().stream()
                .map(acc -> (AccountResDto) acc)
                .collect(Collectors.toList());
    }
}
