package com.springframework.csscapstone.services;

import com.springframework.csscapstone.data.status.RequestStatus;
import com.springframework.csscapstone.payload.request_dto.collaborator.RequestSellingProductCreatorDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.RequestSellingProductResDto;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RequestSellingProductService {

    List<RequestSellingProductResDto> getAllRequest();

    //todo Enterprise role:
    //todo Collaborators role:
    PageImplResDto<RequestSellingProductResDto> getAllRequestByIdCreator(UUID id, Integer pageNumber, Integer pageSize);

    UUID createRequestSellingProduct(RequestSellingProductCreatorDto dto) throws AccountNotFoundException;

    //todo Enterprise role:
    PageImplResDto<RequestSellingProductResDto> getAllRequestByStatus(
            UUID enterpriseId, RequestStatus status, Integer pageNumber, Integer pageSize);

    Optional<UUID> updateProduct(UUID idRequest, RequestStatus status);
}
