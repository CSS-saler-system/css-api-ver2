package com.springframework.csscapstone.css_business.model_dto.basic;

import com.springframework.csscapstone.css_data.status.OrderStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderDto implements Serializable {
    private final UUID id;
    private final LocalDateTime createDate;
    private final double totalPrice;
    private final double totalPointSale;
    private final String customerName;
    private final String deliveryPhone;
    private final String deliveryAddress;
    private final OrderStatus status;
}
