package com.springframework.csscapstone.services;

import com.springframework.csscapstone.payload.request_dto.TransactionsUpdateReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.TransactionsReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.TransactionsResDto;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionServices {

    PageImplResDto<TransactionsResDto> getAllTransaction(Integer pageNumber, Integer pageSize);

    Optional<TransactionsResDto> getTransactionById(UUID id);

    UUID createTransaction(TransactionsReqDto dto, List<MultipartFile> images);

    UUID updateTransaction(TransactionsUpdateReqDto dto, List<MultipartFile> iamges);

    UUID rejectTransaction(UUID id);

    void deleteTransaction(UUID id);

}
