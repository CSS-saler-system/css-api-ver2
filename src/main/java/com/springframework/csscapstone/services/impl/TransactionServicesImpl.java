package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.BillImage;
import com.springframework.csscapstone.data.domain.Transactions;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.data.repositories.BillImageRepository;
import com.springframework.csscapstone.data.repositories.TransactionsRepository;
import com.springframework.csscapstone.data.status.TransactionStatus;
import com.springframework.csscapstone.payload.request_dto.TransactionsUpdateReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.TransactionsReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.TransactionsResDto;
import com.springframework.csscapstone.services.TransactionServices;
import com.springframework.csscapstone.utils.blob_utils.BlobUploadImages;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.transaction_exceptions.TransactionNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.MapperDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServicesImpl implements TransactionServices {

    private final TransactionsRepository transactionsRepository;
    private final AccountRepository accountRepository;

    @Value("${transaction_image_container}")
    private String transactionContainer;

    @Value("${endpoint}")
    private String endpoint;

    private final BlobUploadImages blobUploadImages;

    private final BillImageRepository billImageRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public PageImplResDto<TransactionsResDto> getAllTransaction(Integer pageNumber, Integer pageSize) {

        pageNumber = Objects.isNull(pageNumber) || pageNumber < 1 ? 1 : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize < 1 ? 10 : pageSize;

        Page<Transactions> page = this.transactionsRepository
                .findAll(PageRequest.of(pageNumber - 1, pageSize));

        List<TransactionsResDto> collect = page.getContent()
                .stream().map(MapperDTO.INSTANCE::toTransactionsResDto)
                .collect(Collectors.toList());

        return new PageImplResDto<>(
                collect, page.getNumber() + 1, collect.size(),
                page.getTotalElements(), page.getTotalPages(),
                page.isFirst(), page.isLast());
    }

    @Override
    public Optional<TransactionsResDto> getTransactionById(UUID id) {
        TransactionsResDto transactionsResDto = this.transactionsRepository.findById(id)
                .map(MapperDTO.INSTANCE::toTransactionsResDto)
                .orElseThrow(() -> new TransactionNotFoundException("The transaction with id: " + id + " not found"));
        return Optional.of(transactionsResDto);
    }
    @Transactional
    @Override
    public UUID createTransaction(TransactionsReqDto dto, List<MultipartFile> images) {
        Set<Account> accounts = dto.getAccount()
                .stream()
                .map(x -> this.accountRepository.findById(x.getId())
                        .orElseThrow(() -> new EntityNotFoundException("The json not have id of account!!!")))
                .collect(Collectors.toSet());
        Transactions entity = new Transactions()
                .setPoint(dto.getPoint())
                .addAccount(accounts)
                .setTransactionStatus(TransactionStatus.PENDING);
        Transactions savedTransaction = this.transactionsRepository.save(entity);
        //save images:
        Optional<BillImage> billImage = handlerImages(images, savedTransaction.getId());

        billImage.ifPresent(image -> {
            entity.addImages(image);
            this.transactionsRepository.save(entity);
        });

        return entity.getId();
    }

    public Optional<BillImage> handlerImages(List<MultipartFile> images, UUID id) {
        String imagePrefix = id + "/";
        Map<String, MultipartFile> imageMap = images.stream().collect(
                Collectors.toMap(
                        x -> imagePrefix + x.getOriginalFilename(),
                        x -> x));
        imageMap.forEach(blobUploadImages::azureTransactionStorageHandler);
        return imageMap.keySet()
                .stream()
                .peek(image -> LOGGER.info("The path image of transaction: {}",
                        endpoint + transactionContainer + "/" + image))
                .map(image -> new BillImage(endpoint + transactionContainer + "/" + image))
                .peek(this.billImageRepository::save)
                .findFirst();
    }

    @Transactional
    @Override
    public UUID updateTransaction(TransactionsUpdateReqDto dto, List<MultipartFile> images) {
        Transactions transactions = this.transactionsRepository.findById(dto.getId())
                .orElseThrow(() -> new TransactionNotFoundException("The transaction with id: " + dto.getId() + " not found"));

        Set<Account> accounts = dto.getAccount()
                .stream()
                .map(TransactionsReqDto.AccountNestedDto::getId)
                .map(uuid -> this.accountRepository
                        .findById(uuid)
                        .orElseThrow(() -> new EntityNotFoundException("The account inside transaction dto not found")))
                .collect(Collectors.toSet());

        transactions.setPoint(dto.getPoint())
                .setAccount(accounts)
                .setTransactionStatus(dto.getStatus());
        Optional<BillImage> billImage = handlerImages(images, transactions.getId());
        billImage.ifPresent(image -> {
            transactions.addImages(image);
            this.transactionsRepository.save(transactions);
        });

        return transactions.getId();
    }
    @Transactional
    @Override
    public UUID rejectTransaction(UUID id) {
        Transactions transactions = this.transactionsRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("The transaction with id: " + id + " not found"));

        return this.transactionsRepository.save(transactions.setTransactionStatus(TransactionStatus.REJECT)).getId();
    }
    @Transactional
    @Override
    public void deleteTransaction(UUID id) {
        Transactions transactions = this.transactionsRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("The transaction with id: " + id + " not found"));

        this.transactionsRepository.save(transactions.setTransactionStatus(TransactionStatus.DISABLED));
    }
}