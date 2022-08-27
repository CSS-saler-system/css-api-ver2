package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.services.ModeratorHomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModeratorHomeServiceImpl implements ModeratorHomeService {
    private final AccountRepository accountRepository;

    @Override
    public long countEnterprise() {
        return accountRepository.countEnterprise();
    }

    @Override
    public long countCollaborator() {
        return accountRepository.countCollaborator();
    }
}
