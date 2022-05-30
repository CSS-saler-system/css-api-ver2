package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Order;
import com.springframework.csscapstone.data.domain.OrderDetail;
import com.springframework.csscapstone.data.repositories.AccountRepository;
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
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;

    @Override
    public Optional<UUID> updateOrder(UUID id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> handlerOrderNotFound().get());
        order.setStatus(status);
        this.orderRepository.save(order);
        return Optional.of(order.getId());
    }


    //todo close order to convert point to collaborators:

    /**
     * Order -> Order-Detail: (total point)
     *
     * @param uuid
     */
    @Override
    public void completedOrder(UUID uuid) {
        Order order = this.orderRepository.findById(uuid)
                .filter(_order -> _order.getStatus() != OrderStatus.FINISH)
                .orElseThrow(() -> handlerOrderNotFound().get());

        Account collaborator = order.getAccount();

//        Double totalPoint = order.getOrderDetails()
//                .stream()
//                .map(OrderDetail::getTotalPointProduct)
//                .mapToDouble(Double::doubleValue)
//                .sum();

        Double totalPoint = order.getOrderDetails()
                .stream()
                .map(OrderDetail::getTotalPointProduct)
                .reduce(0.0, Double::sum);

        //todo find enterprise:
        Optional<Account> enterprise = order.getOrderDetails()
                .stream()
                .map(detail -> detail.getProduct().getAccount())
                .findFirst();

        //todo point of enterprise must be large enough
        enterprise.ifPresent(_enterprise -> {
            _enterprise.setPoint(_enterprise.getPoint() - totalPoint);
            collaborator.setPoint(collaborator.getPoint() + totalPoint);
            this.accountRepository.save(collaborator);
            this.accountRepository.save(_enterprise);
        });
    }

    private Supplier<OrderNotFoundException> handlerOrderNotFound() {
        return () -> new OrderNotFoundException(
                MessagesUtils.getMessage(MessageConstant.Order.NOT_FOUND));
    }
}
