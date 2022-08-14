package com.springframework.csscapstone.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springframework.csscapstone.config.firebase_config.FirebaseMessageAsyncUtils;
import com.springframework.csscapstone.config.firebase_config.model.PushNotificationRequest;
import com.springframework.csscapstone.config.message.constant.MessageConstant;
import com.springframework.csscapstone.config.message.constant.MobileScreen;
import com.springframework.csscapstone.data.dao.specifications.RequestSellingProductSpecifications;
import com.springframework.csscapstone.data.domain.*;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class RequestSellingProductServiceImpl implements RequestSellingProductService {
    private final RequestSellingProductRepository requestSellingProductRepository;
    private final ProductRepository productRepository;
    private final AccountRepository accountRepository;
    private final CacheManager cacheManager;
    private final FirebaseMessageAsyncUtils firebaseMessageAsyncUtils;

    private final AccountTokenRepository accountTokenRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final int INVALID_PAGE_NUMBER = 1;
    private static final int INVALID_PAGE_SIZE = 1;
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

    private final Supplier<RuntimeException> noTokenException =
            () -> new RuntimeException(MessagesUtils.getMessage(MessageConstant.Account.NOT_FOUND_TOKEN));

    private final Supplier<RequestNotFoundException> handlerRequestNotFound =
            () -> new RequestNotFoundException(MessagesUtils.getMessage(MessageConstant.RequestSellingProduct.NOT_CREATE_STATUS));
    private static final String REQUEST_BY_ID = "requestById";
    private static final String LIST_REQUEST = "getAllRequest";
    private static final String REQUEST_BY_COL = "requestByCollaborator";
    private static final String REQUEST_BY_STATUS_AND_ENTERPRISE = "requestByStatusAndEnterprise";

    private void clearCache() {
        Objects.requireNonNull(this.cacheManager.getCache(REQUEST_BY_ID)).clear();
        Objects.requireNonNull(this.cacheManager.getCache(LIST_REQUEST)).clear();
        Objects.requireNonNull(this.cacheManager.getCache(REQUEST_BY_COL)).clear();
        Objects.requireNonNull(this.cacheManager.getCache(REQUEST_BY_STATUS_AND_ENTERPRISE)).clear();
    }

    @Override
    @Cacheable(key = "'getAllRequest'", value = LIST_REQUEST)
    public List<RequestSellingProductResDto> getAllRequest() {
        return requestSellingProductRepository.findAll()
                .stream()
                .map(MapperDTO.INSTANCE::toRequestSellingProductResDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(key = "{#p0, #p1, #p2, #p3}", value = REQUEST_BY_COL)
    public PageImplResDto<RequestSellingProductResDto> getAllRequestByIdCreatorByCollaborator(
            UUID idCollaborator, RequestStatus status, Integer pageNumber, Integer pageSize) {

        pageNumber = isNull(pageNumber) || pageNumber < INVALID_PAGE_NUMBER ? DEFAULT_PAGE_NUMBER : pageNumber;
        pageSize = isNull(pageSize) || pageSize < INVALID_PAGE_SIZE ? DEFAULT_PAGE_SIZE : pageSize;

        Specification<RequestSellingProduct> condition = Specification
                .where(RequestSellingProductSpecifications.belongToCollaborator(idCollaborator))
                .and(isNull(status) ? null : RequestSellingProductSpecifications.equalsStatus(status));

        Page<RequestSellingProduct> page = this.requestSellingProductRepository.findAll(condition,
                PageRequest.of(pageNumber - 1, pageSize, Sort.by(RequestSellingProduct_.DATE_TIME_REQUEST).descending()));

        List<RequestSellingProductResDto> content = page.getContent()
                .stream().map(MapperDTO.INSTANCE::toRequestSellingProductResDto)
                .collect(Collectors.toList());

        return new PageImplResDto<>(content, page.getNumber() + 1, content.size(),
                page.getTotalElements(), page.getTotalPages(), page.isFirst(), page.isLast());
    }

    //    @CachePut(value = "requestByCollaborator", key = "'getAllRequestByIdCreatorByCollaborator'")

    @Transactional
    @Override
    public UUID createRequestSellingProduct(RequestSellingProductCreatorDto dto) {
        Product product = this.productRepository.findById(dto.getProduct().getProductId())
                .orElseThrow(() -> new RuntimeException("The product with id: " + dto.getProduct().getProductId() + " not found"));

        Account collaborator = this.accountRepository.findById(dto.getCollaborator().getAccountId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "The account with id: " + dto.getCollaborator().getAccountId() + " not found"));
//
        RequestSellingProduct requestSellingProduct = new RequestSellingProduct()
                .setProduct(product)
                .setAccount(collaborator)
                .setRequestStatus(RequestStatus.CREATED);

        clearCache();
        return this.requestSellingProductRepository.save(requestSellingProduct).getId();
    }

    @Override
    @Cacheable(key = "{#p0, #p1, #p2, #p3}", value = REQUEST_BY_STATUS_AND_ENTERPRISE)
    public PageImplResDto<RequestSellingProductEnterpriseManagerDto> getAllRequestByStatusAndEnterprise(
            UUID enterpriseId, RequestStatus status, Integer pageNumber, Integer pageSize) {

        Specification<RequestSellingProduct> conditions = Specification
                .where(isNull(status) ? null : RequestSellingProductSpecifications.equalsStatus(status))
                .and(RequestSellingProductSpecifications.containsEnterpriseId(enterpriseId));

        pageNumber = nonNull(pageNumber) && (pageNumber >= 1) ? pageNumber : 1;
        pageSize = nonNull(pageSize) && (pageSize >= 1) ? pageSize : 10;

        LOGGER.info("the number: {} - the size: {}", pageNumber, pageSize);


        Page<RequestSellingProduct> page = this.requestSellingProductRepository
                .findAll(conditions, PageRequest.of(pageNumber - 1, pageSize, Sort.by(RequestSellingProduct_.DATE_TIME_REQUEST).descending()));

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
    public Optional<UUID> updateRequestSellingProduct(UUID idRequest, RequestStatus status)
            throws ExecutionException, JsonProcessingException, InterruptedException {
        RequestSellingProduct request = this.requestSellingProductRepository
                .findById(idRequest)
//                .filter(req -> req.getRequestStatus().equals(RequestStatus.CREATED))
                .orElseThrow(handlerRequestNotFound);
        request.setRequestStatus(status);
        RequestSellingProduct save = this.requestSellingProductRepository.save(request);

        String pathImage = request.getProduct().getAccount().getAvatar().getPath();

        AccountToken accountToken = this.accountTokenRepository
                .getAccountTokenByAccountOptional(request.getAccount().getId())
                .map(Collection::stream)
                .orElseGet(Stream::empty)
                .findFirst()
                .orElseThrow(noTokenException);

        sendNotification(save, status, accountToken.getRegistrationToken(), pathImage);
        clearCache();
        return Optional.of(save.getId());
    }


    @Override
    @Cacheable(key = "#p0", value = REQUEST_BY_ID)
    public Optional<RequestSellingProductResDto> getRequestById(UUID uuid) {
        return this.requestSellingProductRepository
                .findById(uuid)
                .map(MapperDTO.INSTANCE::toRequestSellingProductResDto);
    }

    private void sendNotification(RequestSellingProduct request, RequestStatus status, String token, String enterpriseImage)
            throws ExecutionException, JsonProcessingException, InterruptedException {
        PushNotificationRequest notification = new PushNotificationRequest(
                "Request Approval Result",
                "The Request was " + (status.equals(RequestStatus.REJECTED) ? "rejected" : "registered"),
                "The Request",
                token, enterpriseImage);

        Map<String, String> data = new HashMap<>();

        data.put(MobileScreen.REQUEST.getScreen(), request.getId().toString());

        this.firebaseMessageAsyncUtils.sendMessage(data, notification);

    }

}
