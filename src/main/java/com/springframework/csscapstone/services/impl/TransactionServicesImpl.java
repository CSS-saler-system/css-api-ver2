package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.payload.request_dto.enterprise.TransactionsReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.TransactionsResDto;
import com.springframework.csscapstone.services.TransactionServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServicesImpl implements TransactionServices {
    @Override
    public PageImplResDto<TransactionsResDto> getAllTransaction() {
        return null;
    }

    @Override
    public Optional<TransactionsResDto> getTransactionById(UUID id) {
        return Optional.empty();
    }

    @Override
    public UUID createTransaction(TransactionsReqDto dto) {
        return null;
    }

    @Override
    public UUID updateTransaction(TransactionsReqDto dto) {
        return null;
    }

    @Override
    public UUID rejectTransaction(TransactionsReqDto dto) {
        return null;
    }

    @Override
    public void deleteTransaction(TransactionsReqDto dto) {

    }
}
