package com.springframework.csscapstone.utils.mapper_utils;

import com.springframework.csscapstone.data.domain.*;
import com.springframework.csscapstone.payload.basic.*;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResponseDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.EnterpriseResponseDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.CustomerResponseDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapperDTO {
    MapperDTO INSTANCE = Mappers.getMapper(MapperDTO.class);

    //========================= Image ============================
    AccountImageDto toAccountImageDto(AccountImage entity);

    ProductImageDto toProductImageDto(ProductImage entity);


    //========================= End Image ========================
    //========================= Role ============================

    RoleDto toRoleDto(Role entity);

    //========================= End Role ========================



    AccountDto toAccountDto(Account entity);

    CampaignDto toCampaignDto(Campaign entity);

    CategoryDto toCategoryDto(Category entity);

    OrderDto toOrderDto(Order entity);

    PrizeDto toPrizeDto(Prize entity);

    OrderDetailDto toOrderDetailDto(OrderDetail entity);

    ProductDto toProductDto(Product entity);

    RequestSellingProductDto RequestSellingProductDto(RequestSellingProduct entity);

    TransactionsDto toTransactionsDto(Transactions entity);

    //TODO Response DTO
    //TODO [ROLE] - Collaborator
    CustomerResponseDto toCustomerResponseDto(Customer entity);

    EnterpriseResponseDto toEnterpriseResponseDto(Account entity);

    //todo mapper entity to <ENTERPRISE_DTO>
    EnterpriseDto toEnterpriseDto(Account entity);
    //todo mapper entity to <ProductResponseDTO>
    ProductResponseDto toProductResponseDto(Product entity);

    //TODO Admin Role convert to AccountResponseDTO
    AccountResponseDto toAccountResponseDto(Account entity);

}
