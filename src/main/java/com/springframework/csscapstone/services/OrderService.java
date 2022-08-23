package com.springframework.csscapstone.services;

import com.springframework.csscapstone.data.status.OrderStatus;
import com.springframework.csscapstone.payload.request_dto.collaborator.OrderCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.collaborator.OrderUpdaterDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface OrderService {

    OrderResDto getOrderResDtoById(UUID id);

    //todo Collaborator

    /**
     * Update Order
     * Delete Order
     */

    //List product on status, order by point sale
    PageImplResDto<OrderResDto> pageOrderOfCollaborator(
            UUID idCollaborator, OrderStatus orderStatus, Integer pageNumber, Integer pageSize);

    //Create Order Checking same enterprise
    UUID createOrder(OrderCreatorReqDto dto);

    //Update Order
    UUID updateOrder(OrderUpdaterDto dto);

    //Delete Order
    void deleteOrder(UUID id);

    //todo Enterprise
    Optional<UUID> updateStatusOrder(UUID id, OrderStatus status);

    void completedOrder(UUID uuid);


    PageImplResDto<OrderEnterpriseManageResDto> getOrderResDtoByEnterprise(
            UUID enterpriseId, OrderStatus orderStatus, Integer pageNumber, Integer pageSize);

    Optional<List<EnterpriseRevenueDto>> getRevenue(UUID enterpriseId);

    Map<String, OrderChartEnterpriseResDto> getTotalOrderByEnterpriseId(UUID enterpriseId);

    Map<String, RevenueChartEnterpriseResDto> getTotalRevenueByEnterpriseId(UUID enterpriseId);
}
