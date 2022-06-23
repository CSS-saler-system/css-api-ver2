package com.springframework.csscapstone.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.config.firebase_config.FirebaseMessageService;
import com.springframework.csscapstone.config.firebase_config.model.PushNotificationRequest;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.AccountToken;
import com.springframework.csscapstone.data.domain.Product;
import com.springframework.csscapstone.data.domain.RequestSellingProduct;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.data.repositories.AccountTokenRepository;
import com.springframework.csscapstone.data.repositories.ProductRepository;
import com.springframework.csscapstone.data.repositories.RequestSellingProductRepository;
import com.springframework.csscapstone.data.status.RequestStatus;
import com.springframework.csscapstone.payload.request_dto.collaborator.RequestSellingProductCreatorDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.RequestSellingProductResDto;
import com.springframework.csscapstone.services.RequestSellingProductService;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.RequestNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.MapperDTO;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestSellingProductServiceImpl implements RequestSellingProductService {
    private final RequestSellingProductRepository requestSellingProductRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;

    private final FirebaseMessageService firebaseMessageService;

    private final AccountTokenRepository accountTokenRepository;

    @Override
    public List<RequestSellingProductResDto> getAllRequest() {
        return requestSellingProductRepository.findAll()
                .stream()
                .map(MapperDTO.INSTANCE::toRequestSellingProductResDto)
                .collect(Collectors.toList());
    }

    @Override
    public PageImplResDto<RequestSellingProductResDto> getAllRequestByIdCreator(
            UUID id, Integer pageNumber, Integer pageSize) {
        pageNumber = Objects.isNull(pageNumber) || pageNumber < 1 ? 1 : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize < 1 ? 1 : pageSize;

        Page<RequestSellingProduct> page = this.requestSellingProductRepository
                .findRequestSellingProductByCollaborator(id, PageRequest.of(pageNumber - 1, pageSize));

        List<RequestSellingProductResDto> content = page.getContent()
                .stream().map(MapperDTO.INSTANCE::toRequestSellingProductResDto).collect(Collectors.toList());

        return new PageImplResDto<>(content, page.getNumber() + 1, content.size(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Transactional
    @Override
    public UUID createRequestSellingProduct(RequestSellingProductCreatorDto dto) {
        Product product = this.productRepository.findById(dto.getProduct().getProductId())
                .orElseThrow(() -> new RuntimeException("The product with id: " + dto.getProduct().getProductId() + " not found"));

        Account collaborator = this.accountRepository.findById(dto.getCollaborator().getAccountId())
                .orElseThrow(() -> new EntityNotFoundException("The account with id: " + dto.getCollaborator().getAccountId()
                        + " not found"));
//
        RequestSellingProduct requestSellingProduct = new RequestSellingProduct()
                .setProduct(product)
                .setAccount(collaborator).setRequestStatus(RequestStatus.PENDING);
//      todo send notification
//        Map<String, String> msg = new HashMap<>();
//
//        UUID enterpriseId = product.getAccount().getId();
//        AccountToken accountToken = this.accountTokenRepository
//                .getAccountTokenByAccountSortByDate(enterpriseId)
//                .stream()
//                .max(Comparator.comparing(AccountToken::getUpdateTokenDate))
//                .orElseThrow(() -> new RuntimeException("No have token in database"));
//
//        msg.put("Collaborator Name", collaborator.getName());
//        msg.put("Collaborator Phone", collaborator.getPhone());
//        msg.put("Product Id", product.getId().toString());
//        msg.put("Product Name", product.getName());
//
//        firebaseMessageService.sendMessage(msg, new PushNotificationRequest("Selling Request",
//                "Collaborator registers sell product", "Request Selling",
//                accountToken.getRegistrationToken()));

        return this.requestSellingProductRepository.save(requestSellingProduct).getId();
    }

    @Override
    public PageImplResDto<RequestSellingProductResDto> getAllRequestByStatus(
            UUID enterpriseId, RequestStatus status, Integer pageNumber, Integer pageSize) {

        pageSize = Objects.nonNull(pageSize) && pageSize > 1 ? pageSize : 10;
        pageNumber = Objects.nonNull(pageNumber) && pageNumber > 1 ? pageNumber : 1;

        Page<RequestSellingProduct> page = this.requestSellingProductRepository
                .findAllByRequestStatus(enterpriseId, status, PageRequest.of(pageNumber - 1, pageSize));
        List<RequestSellingProductResDto> data = page
                .getContent().stream()
                .map(MapperDTO.INSTANCE::toRequestSellingProductResDto)
                .collect(Collectors.toList());

        return new PageImplResDto<>(data,
                page.getNumber() + 1, data.size(), page.getTotalElements(),
                page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Transactional
    @Override
    public Optional<UUID> updateProduct(UUID idRequest, RequestStatus status) {
        RequestSellingProduct request = this.requestSellingProductRepository
                .findById(idRequest)
                .orElseThrow(() -> handlerRequestNotFound().get());
        request.setRequestStatus(status);
        RequestSellingProduct save = this.requestSellingProductRepository.save(request);
        return Optional.of(save.getId());
    }

    @Override
    public Optional<RequestSellingProductResDto> getRequestById(UUID uuid) {
        return this.requestSellingProductRepository.findById(uuid)
                .map(MapperDTO.INSTANCE::toRequestSellingProductResDto);
    }

    private Supplier<RequestNotFoundException> handlerRequestNotFound() {
        return () -> new RequestNotFoundException(MessagesUtils.getMessage(MessageConstant.RequestSellingProduct.NOT_FOUND));
    }
}
