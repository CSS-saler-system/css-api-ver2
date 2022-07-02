package com.springframework.csscapstone.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springframework.csscapstone.data.domain.RequestSellingProductEnterpriseManagerDto;
import com.springframework.csscapstone.data.status.RequestStatus;
import com.springframework.csscapstone.payload.request_dto.collaborator.RequestSellingProductCreatorDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.RequestSellingProductResDto;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface RequestSellingProductService {

    List<RequestSellingProductResDto> getAllRequest();

    //todo Enterprise role:
    //todo Collaborators role:
    PageImplResDto<RequestSellingProductResDto> getAllRequestByIdCreatorByCollaborator(UUID id, RequestStatus status, Integer pageNumber, Integer pageSize);

    UUID createRequestSellingProduct(RequestSellingProductCreatorDto dto) throws AccountNotFoundException, ExecutionException, JsonProcessingException, InterruptedException;

    //todo Enterprise role:
    PageImplResDto<RequestSellingProductEnterpriseManagerDto> getAllRequestByStatusAndEnterprise(
            UUID enterpriseId, RequestStatus status, Integer pageNumber, Integer pageSize);

    Optional<UUID> updateProduct(UUID idRequest, RequestStatus status);

    Optional<RequestSellingProductResDto> getRequestById(UUID uuid);
}
