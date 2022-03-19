package com.springframework.csscapstone.utils.mapper_utils;

import com.springframework.csscapstone.controller.domain.*;
import com.springframework.csscapstone.services.model_dto.basic.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MapperDTO {
    MapperDTO INSTANCE = Mappers.getMapper(MapperDTO.class);

    AccountDto toAccountDto(Account dto);

    CampaignDto toCampaignDto(Campaign dto);

    CategoryDto toCategoryDto(Category dto);

    OrderDto toOrderDto(Order dto);

    PrizeDto toPrizeDto(Prize dto);

    OrderDetailDto toOrderDetailDto(OrderDetail dto);

    ProductDto toProductDto(Product dto);

    RequestSellingProductDto RequestSellingProductDto(RequestSellingProduct dto);

    RoleDto toRoleDto(Role dto);

    TransactionsDto toTransactionsDto(Transactions dto);
}
