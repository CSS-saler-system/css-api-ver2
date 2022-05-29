package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.domain.RequestSellingProduct;
import com.springframework.csscapstone.data.repositories.RequestSellingProductRepository;
import com.springframework.csscapstone.data.status.RequestStatus;
import com.springframework.csscapstone.payload.response_dto.PageImplResponse;
import com.springframework.csscapstone.payload.response_dto.enterprise.RequestSellingProductDto;
import com.springframework.csscapstone.services.RequestSellingProductService;
import com.springframework.csscapstone.utils.exception_utils.RequestNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.MapperDTO;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestSellingProductServiceImpl implements RequestSellingProductService {
    private final RequestSellingProductRepository requestSellingProductRepository;

    @Override
    public List<RequestSellingProductDto> getAllRequest() {
        return requestSellingProductRepository.findAll()
                .stream()
                .map(MapperDTO.INSTANCE::toRequestSellingProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public PageImplResponse<RequestSellingProductDto> getAllRequestByStatus(RequestStatus status, Integer pageNumber, Integer pageSize) {
        pageSize = Objects.nonNull(pageSize) && pageSize > 1 ? pageSize : 10;
        pageNumber = Objects.nonNull(pageNumber) && pageNumber > 1 ? pageNumber : 1;

        Page<RequestSellingProduct> page = this.requestSellingProductRepository
                .findAllByRequestStatus(status, PageRequest.of(pageNumber - 1, pageSize));
        List<RequestSellingProductDto> data = page
                .getContent().stream()
                .map(MapperDTO.INSTANCE::toRequestSellingProductDto)
                .collect(Collectors.toList());

        return new PageImplResponse<>(data, page.getNumber() + 1, data.size(), page.getTotalElements(),
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

    private Supplier<RequestNotFoundException> handlerRequestNotFound() {
        return () -> new RequestNotFoundException(MessagesUtils.getMessage(MessageConstant.RequestSellingProduct.NOT_FOUND));
    }
}
