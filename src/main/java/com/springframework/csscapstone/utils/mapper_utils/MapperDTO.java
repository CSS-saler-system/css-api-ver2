package com.springframework.csscapstone.utils.mapper_utils;

import com.springframework.csscapstone.data.domain.*;
import com.springframework.csscapstone.payload.basic.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapperDTO {
    MapperDTO INSTANCE = Mappers.getMapper(MapperDTO.class);

    AccountDto toAccountDto(Account entity);

    CampaignDto toCampaignDto(Campaign entity);

    CategoryDto toCategoryDto(Category entity);

    OrderDto toOrderDto(Order entity);

    PrizeDto toPrizeDto(Prize entity);

    OrderDetailDto toOrderDetailDto(OrderDetail entity);

    ProductDto toProductDto(Product entity);

    RequestSellingProductDto RequestSellingProductDto(RequestSellingProduct entity);

    RoleDto toRoleDto(Role entity);

    TransactionsDto toTransactionsDto(Transactions entity);

    //TODO Response DTO
    CustomerResponseDto toCustomerResponseDto(Customer entity);

}
