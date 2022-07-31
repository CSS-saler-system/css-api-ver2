package com.springframework.csscapstone.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.config.constant.MobileScreen;
import com.springframework.csscapstone.config.firebase_config.FirebaseMessageService;
import com.springframework.csscapstone.config.firebase_config.model.PushNotificationRequest;
import com.springframework.csscapstone.data.dao.specifications.RequestSellingProductSpecifications;
import com.springframework.csscapstone.data.domain.*;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.data.repositories.AccountTokenRepository;
import com.springframework.csscapstone.data.repositories.ProductRepository;
import com.springframework.csscapstone.data.repositories.RequestSellingProductRepository;
import com.springframework.csscapstone.data.status.CampaignStatus;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RequestSellingProductServiceImpl implements RequestSellingProductService {
    private final RequestSellingProductRepository requestSellingProductRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;

    private final FirebaseMessageService firebaseMessageService;

    private final AccountTokenRepository accountTokenRepository;
    private static final int INVALID_PAGE_NUMBER = 1;
    private static final int INVALID_PAGE_SIZE = 1;
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 1;

    private final Supplier<RuntimeException> noTokenException = () -> new RuntimeException("No have token belong to this account");

    @Override
    public List<RequestSellingProductResDto> getAllRequest() {
        return requestSellingProductRepository.findAll()
                .stream()
                .map(MapperDTO.INSTANCE::toRequestSellingProductResDto)
                .collect(Collectors.toList());
    }

    @Override
    public PageImplResDto<RequestSellingProductResDto> getAllRequestByIdCreatorByCollaborator(
            UUID idCollaborator, RequestStatus status, Integer pageNumber, Integer pageSize) {

        pageNumber = Objects.isNull(pageNumber) || pageNumber < INVALID_PAGE_NUMBER ? DEFAULT_PAGE_NUMBER : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize < INVALID_PAGE_SIZE ? DEFAULT_PAGE_SIZE : pageSize;

        Page<RequestSellingProduct> page = this.requestSellingProductRepository
                .findRequestSellingProductByCollaborator(idCollaborator, status, PageRequest.of(pageNumber - 1, pageSize));

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
                .setAccount(collaborator)
                .setRequestStatus(RequestStatus.CREATED);
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
    public PageImplResDto<RequestSellingProductEnterpriseManagerDto> getAllRequestByStatusAndEnterprise(
            UUID enterpriseId, RequestStatus status, Integer pageNumber, Integer pageSize) {

        Specification<RequestSellingProduct> conditions = Specification
                .where(Objects.isNull(status) ? null : RequestSellingProductSpecifications.equalsStatus(status))
                .and(RequestSellingProductSpecifications.containsEnterpriseId(enterpriseId));

        pageSize = Objects.nonNull(pageSize) && pageSize > 1 ? pageSize : 10;
        pageNumber = Objects.nonNull(pageNumber) && pageNumber > 1 ? pageNumber : 1;

        Page<RequestSellingProduct> page = this.requestSellingProductRepository
                .findAll(conditions, PageRequest.of(pageNumber - 1, pageSize));

        List<RequestSellingProductEnterpriseManagerDto> data = page
                .getContent().stream()
                .sorted(Comparator.comparing(RequestSellingProduct::getDateTimeRequest).reversed())
                .map(MapperDTO.INSTANCE::toRequestSellingProductEnterpriseManagerDto)
                .collect(Collectors.toList());

        return new PageImplResDto<>(data,
                page.getNumber() + 1, data.size(), page.getTotalElements(),
                page.getTotalPages(), page.isFirst(), page.isLast());
    }

    @Transactional
    @Override
    public Optional<UUID> updateProduct(UUID idRequest, RequestStatus status) throws ExecutionException, JsonProcessingException, InterruptedException {
        RequestSellingProduct request = this.requestSellingProductRepository
                .findById(idRequest)
                .filter(reqeust -> reqeust.getRequestStatus().equals(RequestStatus.CREATED))
                .orElseThrow(() -> handlerRequestNotFound().get());
        request.setRequestStatus(status);
        RequestSellingProduct save = this.requestSellingProductRepository.save(request);
        AccountToken accountToken = this.accountTokenRepository.getAccountTokenByAccountOptional(request.getAccount().getId())
                .map(Collection::stream)
                .orElseGet(Stream::empty)
                .findFirst()
                .orElseThrow(noTokenException);

        sendNotification(save, status, accountToken.getRegistrationToken());

        return Optional.of(save.getId());
    }

    private void sendNotification(RequestSellingProduct request, RequestStatus status, String token) throws ExecutionException, JsonProcessingException, InterruptedException {
        PushNotificationRequest notification = new PushNotificationRequest(
                "Campaign Approval Result",
                "The campaign was " + (status.equals(RequestStatus.REJECT) ? "rejected" : "registered"),
                "The Campaign",
                token);

        Map<String, String> data = new HashMap<>();

        data.put(MobileScreen.REQUEST.getScreen(), request.getId().toString());

        this.firebaseMessageService.sendMessage(data, notification);

    }

    @Override
    public Optional<RequestSellingProductResDto> getRequestById(UUID uuid) {
        return this.requestSellingProductRepository
                .findById(uuid)
                .map(MapperDTO.INSTANCE::toRequestSellingProductResDto);
    }

    private Supplier<RequestNotFoundException> handlerRequestNotFound() {
        return () -> new RequestNotFoundException(MessagesUtils.getMessage(MessageConstant.RequestSellingProduct.NOT_FOUND));
    }
}
