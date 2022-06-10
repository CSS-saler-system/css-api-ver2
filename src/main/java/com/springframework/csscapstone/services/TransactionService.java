package com.springframework.csscapstone.services;

import com.springframework.csscapstone.payload.request_dto.enterprise.TransactionsReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.TransactionsResDto;

import java.util.Optional;
import java.util.UUID;

public interface TransactionService {
    PageImplResDto<TransactionsResDto> getAllTransaction();

    Optional<TransactionsResDto> getTransactionById(UUID id);

    UUID createTransaction(TransactionsReqDto dto);
    UUID updateTransaction(TransactionsReqDto dto);

    UUID rejectTransaction(TransactionsReqDto dto);

    void deleteTransaction(TransactionsReqDto dto);

}
