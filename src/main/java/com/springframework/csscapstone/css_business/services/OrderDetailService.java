package com.springframework.csscapstone.css_business.services;

import com.springframework.csscapstone.css_business.model_dto.custom.creator_model.OrderDetailCreator;
import com.springframework.csscapstone.css_business.model_dto.custom.update_model.OrderDetailUpdater;
import com.springframework.csscapstone.css_data.domain.OrderDetail;
import com.springframework.csscapstone.utils.exception_utils.order_detail_exception.OrderDetailException;
import com.springframework.csscapstone.utils.exception_utils.order_detail_exception.ProductCanCreateException;
import com.springframework.csscapstone.utils.exception_utils.order_exception.OrderNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;

import java.util.List;
import java.util.UUID;

public interface OrderDetailService {
    List<OrderDetail> findAll();

    OrderDetail findById(UUID id);

    UUID createOrderDetail(OrderDetailCreator dto) throws ProductNotFoundException, OrderNotFoundException, ProductCanCreateException, OrderDetailException;

    UUID updateOrderDetail(UUID id, OrderDetailUpdater dto) throws OrderDetailException, ProductNotFoundException;

    void delete(UUID id) throws OrderDetailException;

}
