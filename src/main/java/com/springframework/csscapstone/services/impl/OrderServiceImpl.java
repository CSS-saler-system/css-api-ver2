package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Order;
import com.springframework.csscapstone.data.repositories.OrderRepository;
import com.springframework.csscapstone.data.status.OrderStatus;
import com.springframework.csscapstone.services.OrderService;
import com.springframework.csscapstone.utils.exception_utils.order_exception.OrderNotFoundException;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Supplier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Optional<UUID> updateOrder(UUID id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> handlerOrderNotFound().get());
        order.setStatus(status);
        this.orderRepository.save(order);
        return Optional.of(order.getId());
    }

    private Supplier<OrderNotFoundException> handlerOrderNotFound() {
        return () -> new OrderNotFoundException(
                MessagesUtils.getMessage(MessageConstant.Order.NOT_FOUND));
    }

    @Override
    public void completedOrder(UUID uuid) {
        Order order = this.orderRepository.findById(uuid)
                .orElseThrow(() -> handlerOrderNotFound().get());

        Account account = order.getAccount();
//        order.getOrderDetails()
//                .stream()
//                .collect(toMap(x -> x.getProduct().getId(), x -> x.getQuantity()))
    }
}
