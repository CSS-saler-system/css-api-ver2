package com.springframework.csscapstone.services;

import com.springframework.csscapstone.data.status.RequestStatus;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.RequestSellingProductResDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RequestSellingProductService {

    List<RequestSellingProductResDto> getAllRequest();

    PageImplResDto<RequestSellingProductResDto> getAllRequestByStatus(
            UUID enterpriseId, RequestStatus status, Integer pageNumber, Integer pageSize);

    Optional<UUID> updateProduct(UUID idRequest, RequestStatus status);
}
