package com.springframework.csscapstone.utils.mapper_utils;

import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.AccountImage;
import com.springframework.csscapstone.data.domain.Campaign;
import com.springframework.csscapstone.data.domain.Category;
import com.springframework.csscapstone.data.domain.Customer;
import com.springframework.csscapstone.data.domain.Order;
import com.springframework.csscapstone.data.domain.OrderDetail;
import com.springframework.csscapstone.data.domain.Prize;
import com.springframework.csscapstone.data.domain.Product;
import com.springframework.csscapstone.data.domain.ProductImage;
import com.springframework.csscapstone.data.domain.RequestSellingProduct;
import com.springframework.csscapstone.data.domain.Role;
import com.springframework.csscapstone.data.domain.Transactions;
import com.springframework.csscapstone.payload.basic.AccountDto;
import com.springframework.csscapstone.payload.basic.AccountImageDto;
import com.springframework.csscapstone.payload.basic.CampaignDto;
import com.springframework.csscapstone.payload.basic.CategoryDto;
import com.springframework.csscapstone.payload.basic.EnterpriseDto;
import com.springframework.csscapstone.payload.basic.OrderDetailDto;
import com.springframework.csscapstone.payload.basic.OrderDto;
import com.springframework.csscapstone.payload.basic.PrizeDto;
import com.springframework.csscapstone.payload.basic.ProductDto;
import com.springframework.csscapstone.payload.basic.ProductImageDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.RequestSellingProductDto;
import com.springframework.csscapstone.payload.basic.RoleDto;
import com.springframework.csscapstone.payload.basic.TransactionsDto;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResponseDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.EnterpriseResponseDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.CustomerResponseDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResponseDto;
import org.mapstruct.Mapper;
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

    RequestSellingProductDto toRequestSellingProductDto(RequestSellingProduct entity);

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
