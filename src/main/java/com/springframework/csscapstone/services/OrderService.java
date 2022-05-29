package com.springframework.csscapstone.services;

import com.springframework.csscapstone.data.status.OrderStatus;

import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    Optional<UUID> updateOrder(UUID id, OrderStatus status);

    void completedOrder(UUID uuid);

}
