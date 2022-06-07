package com.springframework.csscapstone.utils.mapper_utils.dto_mapper;

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
import com.springframework.csscapstone.payload.basic.*;
import com.springframework.csscapstone.payload.basic.AccountImageBasicDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.RequestSellingProductResDto;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.EnterpriseResDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.CustomerResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.ProductResDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapperDTO {
    MapperDTO INSTANCE = Mappers.getMapper(MapperDTO.class);

    //========================= Image ============================
    AccountImageBasicDto toAccountImageDto(AccountImage entity);

    ProductImageBasicDto toProductImageDto(ProductImage entity);


    //========================= End Image ========================
    //========================= Role ============================

    RoleBasicDto toRoleDto(Role entity);

    //========================= End Role ========================

    AccountBasicDto toAccountDto(Account entity);

    CampaignBasicDto toCampaignDto(Campaign entity);

    CategoryBasicDto toCategoryDto(Category entity);

    OrderBasicDto toOrderDto(Order entity);

    PrizeBasicDto toPrizeDto(Prize entity);

    OrderDetailBasicDto toOrderDetailDto(OrderDetail entity);

    ProductBasicDto toProductDto(Product entity);

    RequestSellingProductResDto toRequestSellingProductDto(RequestSellingProduct entity);

    TransactionsBasicDto toTransactionsDto(Transactions entity);

    //TODO Response DTO
    //TODO [ROLE] - Collaborator
    CustomerResDto toCustomerResponseDto(Customer entity);

    EnterpriseResDto toEnterpriseResponseDto(Account entity);

    //todo mapper entity to <ENTERPRISE_DTO>
    EnterpriseBasicDto toEnterpriseDto(Account entity);
    //todo mapper entity to <ProductResponseDTO>
    ProductResDto toProductResponseDto(Product entity);

    //TODO Admin Role convert to AccountResponseDTO
    AccountResDto toAccountResponseDto(Account entity);



}
