package com.springframework.csscapstone.services;

import com.springframework.csscapstone.data.domain.OrderDetail;
import com.springframework.csscapstone.payload.request_dto.admin.OrderDetailCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.admin.OrderDetailUpdaterReqDto;
import com.springframework.csscapstone.utils.exception_utils.order_detail_exception.OrderDetailException;
import com.springframework.csscapstone.utils.exception_utils.order_detail_exception.ProductCanCreateException;
import com.springframework.csscapstone.utils.exception_utils.order_exception.OrderNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;

import java.util.List;
import java.util.UUID;

public interface OrderDetailService {
    List<OrderDetail> findAll();

    OrderDetail findById(UUID id);

    UUID createOrderDetail(OrderDetailCreatorReqDto dto) throws ProductNotFoundException, OrderNotFoundException, ProductCanCreateException, OrderDetailException;

    UUID updateOrderDetail(UUID id, OrderDetailUpdaterReqDto dto) throws OrderDetailException, ProductNotFoundException;

    void delete(UUID id) throws OrderDetailException;

}
