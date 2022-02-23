package com.springframework.csscapstone.utils.mapper_utils;

import com.springframework.csscapstone.css_business.model_dto.basic.*;
import com.springframework.csscapstone.css_data.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
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
