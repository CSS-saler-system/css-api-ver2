package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.data.repositories.RequestSellingProductRepository;
import com.springframework.csscapstone.services.EnterpriseInformationService;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnterpriseInformationServiceImpl implements EnterpriseInformationService {
    private final AccountRepository accountRepository;
    private final RequestSellingProductRepository requestSellingProductRepository;

    @Override
    public long countCollaborator(UUID enterpriseId) {
        Long countCollab = requestSellingProductRepository.countCollaboratorByEnterprise(enterpriseId);
        return countCollab;
    }

    @Override
    public long countRequestSeliingProduct(UUID enterpriseId) {
        Long result = this.requestSellingProductRepository.countRequestByEnterprise(enterpriseId);
        return result;
    }

    @Override
    public Double getPoint(UUID enterpriseId) {
        return accountRepository.findById(enterpriseId).map(Account::getPoint)
                .orElseThrow(() -> new EntityNotFoundException(String.format("not have enterprise with id %s", enterpriseId)));
    }
}
