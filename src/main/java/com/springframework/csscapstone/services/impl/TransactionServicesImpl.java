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
import com.springframework.csscapstone.payload.request_dto.enterprise.TransactionsUpdateReqDto;
import com.springframework.csscapstone.payload.request_dto.enterprise.TransactionsCreatorReqDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.TransactionsResDto;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
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
    public PageImplResDto<TransactionsResDto> getAllTransaction(
            UUID idEnterprise, LocalDateTime createDate, LocalDateTime modifiedDate,
            Integer pageNumber, Integer pageSize) {

        Specification<Transactions> condition = Specification
                .where(TransactionSpecifications.equalId(idEnterprise))
                .and(Objects.isNull(createDate) ? null : TransactionSpecifications.afterDate(createDate))
                .and(Objects.isNull(modifiedDate) ? null : TransactionSpecifications.beforeDate(modifiedDate))
                //todo transactions not having disable status
                .and((root, query, criteriaBuilder) -> criteriaBuilder
                        .notEqual(root.get(Transactions_.TRANSACTION_STATUS), TransactionStatus.DISABLED));

        pageNumber = Objects.isNull(pageNumber) || pageNumber < 1 ? 1 : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize < 1 ? 10 : pageSize;

        Page<Transactions> page = this.transactionsRepository
                .findAll(condition, PageRequest.of(pageNumber - 1, pageSize));

        List<TransactionsResDto> collect = page.getContent()
                .stream().map(MapperDTO.INSTANCE::toTransactionsResDto)
                .sorted(Comparator.comparing(TransactionsResDto::getLastModifiedDate))
                .collect(Collectors.toList());

        return new PageImplResDto<>(
                collect, page.getNumber() + 1, collect.size(),
                page.getTotalElements(), page.getTotalPages(),
                page.isFirst(), page.isLast());
    }
//                .filter(transactions -> !transactions.getTransactionStatus().equals(TransactionStatus.DISABLED))

    @Override
    public Optional<TransactionsResDto> getTransactionById(UUID id) {
        TransactionsResDto transactionsResDto = this.transactionsRepository.findById(id)
                .filter(transactions -> !transactions.getTransactionStatus().equals(TransactionStatus.DISABLED))
                .map(MapperDTO.INSTANCE::toTransactionsResDto)
                .orElseThrow(() -> new TransactionNotFoundException("The transaction with id: " + id + " not found"));
        return Optional.of(transactionsResDto);
    }

    //todo Enterprise create transaction
    @Transactional
    @Override
    public UUID createTransaction(TransactionsCreatorReqDto dto, List<MultipartFile> images) {
        Account accounts = this.accountRepository
                .findById(dto.getCreator().getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("The json not have id of account!!!"));

        Transactions entity = new Transactions()
                .setPoint(dto.getPoint())
                .setTransactionCreator(accounts)
                .setTransactionStatus(TransactionStatus.PENDING);

        Transactions savedTransaction = this.transactionsRepository.save(entity);

        //save images:
        Optional<BillImage> billImage = handlerImages(images, savedTransaction.getId());

        billImage.ifPresent(image -> {
            savedTransaction.addImages(image);
            this.transactionsRepository.save(savedTransaction);
        });

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

    //todo enterprise update transasction
    @Transactional
    @Override
    public UUID updateTransaction(TransactionsUpdateReqDto dto, List<MultipartFile> images) {
        Transactions transactions = this.transactionsRepository.findById(dto.getId())
                .orElseThrow(() -> new TransactionNotFoundException("The transaction with id: " + dto.getId() + " not found"));

        Set<Account> accounts = dto.getAccount()
                .stream()
                .map(TransactionsUpdateReqDto.AccountNestedDto::getId)
                .map(uuid -> this.accountRepository
                        .findById(uuid)
                        .orElseThrow(() -> new EntityNotFoundException("The account inside transaction dto not found")))
                .collect(Collectors.toSet());

        transactions
                .setPoint(dto.getPoint())
//                todo creator
//                .setTransactionApprover(accounts)
                .setTransactionStatus(dto.getStatus());
        Optional<BillImage> billImage = handlerImages(images, transactions.getId());
        billImage.ifPresent(image -> {
            transactions.addImages(image);
            this.transactionsRepository.save(transactions);
        });

        return transactions.getId();
    }
    //todo moderator create point for enterprise
    @Transactional
    @Override
    public UUID acceptedTransaction(UUID idTransaction) {
        Transactions transactions = this.transactionsRepository.findById(idTransaction)
                .orElseThrow(() -> new RuntimeException("The transaction with id: " + idTransaction + " not found"));

        Account enterprise = transactions.getTransactionCreator();
        enterprise.setPoint(enterprise.getPoint() + transactions.getPoint());
        this.accountRepository.save(enterprise);

        transactions.setTransactionStatus(TransactionStatus.ACCEPT);
        return this.transactionsRepository.save(transactions).getId();
    }

    @Transactional
    @Override
    public UUID rejectTransaction(UUID id) {
        Transactions transactions = this.transactionsRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(
                        "The transaction with id: " + id + " not found"));

        return this.transactionsRepository
                .save(transactions.setTransactionStatus(TransactionStatus.REJECT)).getId();
    }

    @Transactional
    @Override
    public void deleteTransaction(UUID id) {
        Transactions transactions = this.transactionsRepository.findById(id)
                .filter(transaction -> !transaction.getTransactionStatus().equals(TransactionStatus.DISABLED))
                .orElseThrow(() -> new TransactionNotFoundException("The transaction with id: " + id + " not found"));

        this.transactionsRepository.save(transactions.setTransactionStatus(TransactionStatus.DISABLED));
    }
}
