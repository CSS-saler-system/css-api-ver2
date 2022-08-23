package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.data.dao.specifications.TransactionSpecifications;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.BillImage;
import com.springframework.csscapstone.data.domain.Transactions;
import com.springframework.csscapstone.data.domain.Transactions_;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.data.repositories.BillImageRepository;
import com.springframework.csscapstone.data.repositories.TransactionsRepository;
import com.springframework.csscapstone.data.status.TransactionStatus;
import com.springframework.csscapstone.payload.request_dto.enterprise.TransactionsCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.TransactionsUpdateReqDto;
import com.springframework.csscapstone.payload.request_dto.moderator.TransactionHandler;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.TransactionsDto;
import com.springframework.csscapstone.services.TransactionServices;
import com.springframework.csscapstone.utils.blob_utils.BlobUploadImages;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.transaction_exceptions.TransactionNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.MapperDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

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

    LocalDateTime defaultMinDate = LocalDateTime.of(1999, 6, 8, 12, 0, 0, 0);
    LocalDateTime defaultMaxDate = LocalDateTime.of(2099, 6, 8, 12, 0, 0, 0);

    @Transactional
    @Override
    public PageImplResDto<TransactionsDto> getAllTransaction(
            UUID enterpriseId,  TransactionStatus status, LocalDateTime createDate, LocalDateTime modifiedDate,
            Integer pageNumber, Integer pageSize) {

        this.accountRepository.findById(enterpriseId)
                .filter(acc -> acc.getRole().getName().equals("Enterprise"))
                .orElseThrow(() -> new EntityNotFoundException("The enterprise with id: " + enterpriseId + "was not found"));

        pageNumber = isNull(pageNumber) || pageNumber < 1 ? 1 : pageNumber;
        pageSize = isNull(pageSize) || pageSize < 1 ? 10 : pageSize;

        Specification<Transactions> specification = Specification
                .where(isNull(status) ? null : TransactionSpecifications.equalsStatus(status))
                .and(isNull(createDate) ? null : TransactionSpecifications.afterDate(createDate))
                .and(isNull(modifiedDate) ? null : TransactionSpecifications.afterDate(modifiedDate))
                .and(TransactionSpecifications.equalsEnterpriseId(enterpriseId))
                .and(TransactionSpecifications.excludeStatusDisable());

        Page<Transactions> page = this.transactionsRepository
                .findAll(specification, PageRequest.of(pageNumber - 1, pageSize,
                        Sort.by(Transactions_.LAST_MODIFIED_DATE).descending()));

        page.getContent().forEach(System.out::println);
        List<TransactionsDto> collect = page.getContent()
                .stream()
                .sorted(Comparator.comparing(Transactions::getLastModifiedDate))
                .map(MapperDTO.INSTANCE::toTransactionsResDto)
                .collect(Collectors.toList());

        return new PageImplResDto<>(
                collect, page.getNumber() + 1, collect.size(),
                page.getTotalElements(), page.getTotalPages(),
                page.isFirst(), page.isLast());
    }

    @Override
    public Optional<TransactionsDto> getTransactionById(UUID id) {
        TransactionsDto transactionsResDto = this.transactionsRepository.findById(id)
                .filter(transactions -> !transactions.getTransactionStatus().equals(TransactionStatus.DISABLED))
                .map(MapperDTO.INSTANCE::toTransactionsResDto)
                .orElseThrow(() -> new TransactionNotFoundException("The transaction with id: " + id + " not found"));
        return Optional.of(transactionsResDto);
    }

    //todo Enterprise create transaction
    @Transactional
    @Override
    public UUID createTransaction(TransactionsCreatorReqDto dto, List<MultipartFile> images) {
        //check account exist
        Account accounts = this.accountRepository
                .findById(dto.getCreator().getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("The json not have id of account!!!"));

        //check account transaction
        Transactions entity = new Transactions()
                .setPoint(dto.getPoint())
                .setTransactionCreator(accounts)
                .setTransactionStatus(TransactionStatus.CREATED);

        //check account transaction
        Transactions savedTransaction = this.transactionsRepository.save(entity);

        //save images:
        if (Objects.nonNull(images) && !images.isEmpty()) {

            Optional<BillImage> billImage = handlerImages(images, savedTransaction.getId());

            billImage.ifPresent(image -> {
                savedTransaction.addImages(image);
                this.transactionsRepository.save(savedTransaction);
            });

        }

        return savedTransaction.getId();
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

    //todo enterprise update transaction
    @Transactional
    @Override
    public UUID updateTransaction(UUID transactionId, TransactionsUpdateReqDto dto, List<MultipartFile> images) {

        Supplier<TransactionNotFoundException> transactionNotFoundExceptionSupplier = () -> new TransactionNotFoundException("The transaction with id: " + transactionId + " not found");

        Transactions transactions = this.transactionsRepository
                .findById(transactionId)
                .orElseThrow(transactionNotFoundExceptionSupplier);

        boolean isCreatedStatus = transactions.getTransactionStatus().equals(TransactionStatus.CREATED);

        if (!isCreatedStatus) {
            throw new RuntimeException("Transaction with id: " + transactionId + "is not allowed to modified, because maybe it's not in pending status !!!");
        }

        Supplier<EntityNotFoundException> entityNotFoundExceptionSupplier = () -> new EntityNotFoundException("The creator with: " + transactionId + " was not found");
        Account account = this.accountRepository
                .findById(dto.getCreator().getId())
                .orElseThrow(entityNotFoundExceptionSupplier);

        if (!transactions.getTransactionCreator().equals(account)) {
            throw new RuntimeException("The creator is invalid!!!");
        }

        transactions.setPoint(dto.getPoint());

        if (Objects.nonNull(images) && !images.isEmpty()) {
            Optional<BillImage> billImage = handlerImages(images, transactions.getId());
            billImage.ifPresent(image -> {
                transactions.addImages(image);
                this.transactionsRepository.save(transactions);
            });
        }

        return transactions.getId();
    }

    //todo moderator create point for enterprise
    @Transactional
    @Override
    public UUID acceptedTransaction(TransactionHandler handler) {
        Transactions transactions = this.transactionsRepository
                .findById(handler.getTransactionId())
                .orElseThrow(() -> new EntityNotFoundException("The transaction with id: " +
                        handler.getTransactionId() + " not found"));

        Account approver = accountRepository.findById(handler.getAccountApproverId())
                .filter(appr -> appr.getRole().getName().equals("Moderator"))
                .orElseThrow(() -> new EntityNotFoundException("The approver with id: " +
                        handler.getAccountApproverId() + " not found"));

        Account enterprise = transactions.getTransactionCreator();

        enterprise.setPoint(enterprise.getPoint() + transactions.getPoint());

        transactions.setTransactionStatus(TransactionStatus.ACCEPTED);

        this.accountRepository.save(enterprise);
        accountRepository.save(approver.addApproverTransaction(transactions));
        return this.transactionsRepository.save(transactions).getId();
    }

    @Transactional
    @Override
    public UUID rejectTransaction(UUID id) {

        Transactions transactions = this.transactionsRepository
                .findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("The transaction with id: " + id + " not found"));

        return this.transactionsRepository
                .save(transactions.setTransactionStatus(TransactionStatus.REJECTED)).getId();
    }

    @Transactional
    @Override
    public void deleteTransaction(UUID id) {
        Transactions transactions = this.transactionsRepository.findById(id)
                .filter(transaction -> !transaction.getTransactionStatus().equals(TransactionStatus.DISABLED))
                .orElseThrow(() -> new TransactionNotFoundException("The transaction with id: " + id + " not found"));

        this.transactionsRepository.save(transactions.setTransactionStatus(TransactionStatus.DISABLED));
    }

    @Override
    public PageImplResDto<TransactionsDto> getAllPendingStatusList(TransactionStatus status, Integer pageNumber, Integer pageSize) {

        pageNumber = isNull(pageNumber) || pageNumber < 1 ? 1 : pageNumber;
        pageSize = isNull(pageSize) || pageSize < 1 ? 10 : pageSize;

        Specification<Transactions> condition = Specification.where(isNull(status) ? null : TransactionSpecifications.equalsStatus(status));

        Page<Transactions> page = this.transactionsRepository
                .findAll(condition, PageRequest.of(pageNumber - 1, pageSize, Sort.by(Transactions_.CREATE_TRANSACTION_DATE).descending()));

        List<TransactionsDto> pageRes = page.getContent()
                .stream().map(MapperDTO.INSTANCE::toTransactionsResDto)
                .collect(Collectors.toList());

        return new PageImplResDto<>(pageRes, page.getNumber(), pageRes.size(), page.getTotalElements(), page.getTotalPages(),
                page.isFirst(), page.isLast());
    }
}
