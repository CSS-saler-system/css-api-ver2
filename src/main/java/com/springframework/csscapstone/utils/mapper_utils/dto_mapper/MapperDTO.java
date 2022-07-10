package com.springframework.csscapstone.utils.mapper_utils.dto_mapper;

import com.springframework.csscapstone.data.domain.*;
import com.springframework.csscapstone.payload.basic.AccountImageBasicDto;
import com.springframework.csscapstone.payload.basic.CategoryBasicDto;
import com.springframework.csscapstone.payload.basic.ProductImageBasicDto;
import com.springframework.csscapstone.payload.basic.RoleBasicDto;
import com.springframework.csscapstone.payload.request_dto.admin.AccountCreatorReqDto;
import com.springframework.csscapstone.payload.response_dto.AccountTokenDto;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResDto;
import com.springframework.csscapstone.payload.response_dto.admin.CategoryResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.CustomerResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.EnterpriseResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.OrderDetailResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.*;
import com.springframework.csscapstone.services.impl.AccountServiceImpl;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MapperDTO {

    MapperDTO INSTANCE = Mappers.getMapper(MapperDTO.class);

    //========================= Image ============================
    AccountImageBasicDto toAccountImageResDto(AccountImage entity);

    ProductImageBasicDto toProductImageDto(ProductImage entity);


    //========================= End Image ========================
    //========================= Role ============================
    RoleBasicDto toRoleResDto(Role entity);

    //========================= End Role ========================

    @Mapping(target = "campaignId", source = "id")
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
//    @Mapping(target = "quantity", source = "quantityInStock")
    ProductResDto toProductResDto(Product entity);

    AccountTokenDto toAccountTokenDto(AccountToken accountToken);

    //    @Mapping(target = "percentSoldByCategory.key.categoryId", source = "entity.percentSoldByCategory.Category.id")
    @Mapping(target = "percentSoldByCategory.key.categoryName", source = "entity.percentSoldByCategory.key")
    @Mapping(target = "percentSoldByCategory.value", source = "percentSoldByCategory.value")
    @Mapping(target = "avatar.id", source = "entity.avatar.id")
    @Mapping(target = "avatar.type", source = "entity.avatar.type")
    @Mapping(target = "avatar.path", source = "entity.avatar.path")
    CollaboratorWithQuantitySoldByCategoryDto toCollaboratorWithQuantitySoldByCategoryDto(Account entity);


    @Mapping(target = "categoryId", source = "id")
    CategoryBasicDto toCategoryBasicDto(Category entity);

    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "account.accountId", source = "entity.account.id")
    @Mapping(target = "customer.customerId", source = "entity.customer.id")
    OrderEnterpriseManageResDto toOrderEnterpriseManageResDto(Order entity);


    @Mapping(target = "requestId", source = "id")
    @Mapping(target = "product.productID", source = "entity.product.id")
    @Mapping(target = "product.productName", source = "entity.product.name")
    @Mapping(target = "account", source = "entity.account")
    @Mapping(target = "account.collaboratorId", source = "entity.account.id")
    @Mapping(target = "account.collaboratorName", source = "entity.account.name")
    @Mapping(target = "account.collaboratorPhone", source = "entity.account.phone")
    RequestSellingProductEnterpriseManagerDto toRequestSellingProductEnterpriseManagerDto(RequestSellingProduct entity);

    @Mapping(target = "normal", source = "normal")
    @Mapping(target = "certification", source = "certification")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "category.categoryId", source = "category.id")
    @Mapping(target = "category.categoryName", source = "category.categoryName")
    ProductDetailEnterpriseDto toProductDetailEnterpriseDto(Product entity);

    @Mapping(target = "prizes", source = "prizes")
//    @Mapping(target = "prizeInnerCampaignDto.prizeId", source = "entity.prizes.prize.id")
    @Mapping(target = "products", source = "products")
    CampaignDetailDto toCampaignDetailDto(Campaign entity);



}