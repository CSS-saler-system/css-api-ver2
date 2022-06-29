package com.springframework.csscapstone.utils.mapper_utils.dto_mapper;

import com.springframework.csscapstone.data.domain.*;
import com.springframework.csscapstone.payload.basic.AccountImageBasicDto;
import com.springframework.csscapstone.payload.basic.CategoryBasicDto;
import com.springframework.csscapstone.payload.basic.ProductImageBasicDto;
import com.springframework.csscapstone.payload.basic.RoleBasicDto;
import com.springframework.csscapstone.payload.response_dto.AccountTokenDto;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResDto;
import com.springframework.csscapstone.payload.response_dto.admin.CategoryResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.CustomerResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.EnterpriseResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.OrderDetailResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapperDTO {
    MapperDTO INSTANCE = Mappers.getMapper(MapperDTO.class);

    //========================= Image ============================
    AccountImageBasicDto toAccountImageResDto(AccountImage entity);

    ProductImageBasicDto toProductImageDto(ProductImage entity);


    //========================= End Image ========================
    //========================= Role ============================

    RoleBasicDto toRoleResDto(Role entity);

    //========================= End Role ========================

    @Mapping(target = "avatar", source = "avatar")
    @Mapping(target = "licenses", source = "license")
    @Mapping(target = "idCard", source = "idCard")
    AccountResDto toAccountResDto(Account entity);

    @Mapping(target = "campaignPrizes", source = "prizes")
    @Mapping(target = "products", source = "products")
    @Mapping(target = "campaignId", source = "id")
//    @Mapping(target = "prizeId", source = "entity.prizes.id")
//    @Mapping(target = "productId", source = "entity.products.id")
    CampaignResDto toCampaignResDto(Campaign entity);

    CategoryResDto toCategoryResDto(Category entity);

    OrderResDto toOrderResDto(Order entity);

    PrizeResDto toPrizeDto(Prize entity);
    PrizeResDto toPrizeResDto(Prize entity);

    OrderDetailResDto toOrderDetailResDto(OrderDetail entity);

    ProductResDto toProductDto(Product entity);

    RequestSellingProductResDto toRequestSellingProductResDto(RequestSellingProduct entity);

    TransactionsDto toTransactionsResDto(Transactions entity);

    //TODO Response DTO
    //TODO [ROLE] - Collaborator
    CustomerResDto toCustomerResDto(Customer entity);

    @Mapping(target = "avatar", source = "avatar")
    @Mapping(target = "license", source = "license")
    @Mapping(target = "idCard", source = "idCard")
    EnterpriseResDto toEnterpriseResDto(Account entity);

    //todo mapper entity to <ENTERPRISE_DTO>
    @Mapping(target = "avatar", source = "avatar")
    @Mapping(target = "license", source = "license")
    @Mapping(target = "idCard", source = "idCard")
    EnterpriseResDto toEnterpriseDto(Account entity);

    //todo mapper entity to <ProductResponseDTO>
    @Mapping(target = "quantity", source = "quantityInStock")
    ProductResDto toProductResDto(Product entity);

    AccountTokenDto toAccountTokenDto(AccountToken accountToken);

    @Mapping(target = "percentSoldByCategory", source = "percentSoldByCategory")
    CollaboratorWithQuantitySoldByCategoryDto toCollaboratorWithQuantitySoldByCategoryDto(Account entity);


    @Mapping(target = "categoryId", source = "id")
    CategoryBasicDto toCategoryBasicDto(Category entity);

}
