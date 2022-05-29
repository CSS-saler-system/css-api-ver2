package com.springframework.csscapstone.services;

import com.springframework.csscapstone.data.status.RequestStatus;
import com.springframework.csscapstone.payload.response_dto.PageImplResponse;
import com.springframework.csscapstone.payload.response_dto.enterprise.RequestSellingProductDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RequestSellingProductService {

    List<RequestSellingProductDto> getAllRequest();

    PageImplResponse<RequestSellingProductDto> getAllRequestByStatus(RequestStatus status,  Integer pageNumber, Integer pageSize);

    Optional<UUID> updateProduct(UUID idRequest, RequestStatus status);
}
