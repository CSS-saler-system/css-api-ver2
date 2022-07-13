package com.springframework.csscapstone.services;

import com.springframework.csscapstone.data.status.TransactionStatus;
import com.springframework.csscapstone.payload.request_dto.enterprise.TransactionsCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.TransactionsUpdateReqDto;
import com.springframework.csscapstone.payload.request_dto.moderator.TransactionHandler;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.TransactionsDto;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionServices {

    PageImplResDto<TransactionsDto> getAllTransaction(
            UUID idEnterprise, LocalDateTime createDate,
            LocalDateTime modifiedDate, Integer pageNumber, Integer pageSize);

    Optional<TransactionsDto> getTransactionById(UUID id);

    UUID createTransaction(TransactionsCreatorReqDto dto, List<MultipartFile> images);

    UUID updateTransaction(UUID transactionId, TransactionsUpdateReqDto dto, List<MultipartFile> images);

    UUID acceptedTransaction(TransactionHandler handler);

    UUID rejectTransaction(UUID id);

    void deleteTransaction(UUID id);

    PageImplResDto<TransactionsDto> getAllPendingStatusList(Integer pageNumber, Integer pageSize);
}
