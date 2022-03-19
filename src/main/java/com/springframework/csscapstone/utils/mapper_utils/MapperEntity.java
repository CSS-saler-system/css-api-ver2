package com.springframework.csscapstone.utils.mapper_utils;

import com.springframework.csscapstone.data.domain.*;
import com.springframework.csscapstone.services.model_dto.basic.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapperEntity {
    MapperEntity INSTANCE = Mappers.getMapper(MapperEntity.class);

    Account toAccount(AccountDto dto);

    Campaign toCampaign(CampaignDto dto);

    Category toCategory(CategoryDto dto);

    Order toOrder(OrderDto dto);

    Prize toPrize(PrizeDto dto);

    OrderDetail toOrderDetail(OrderDetailDto dto);

    Product toProduct(ProductDto dto);

    RequestSellingProduct RequestSellingProduct(RequestSellingProductDto dto);

    Role toRole(RoleDto dto);

    Transactions toTransactions(TransactionsDto dto);

}
