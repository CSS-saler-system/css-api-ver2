package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.domain.*;
import com.springframework.csscapstone.data.repositories.*;
import com.springframework.csscapstone.data.status.OrderStatus;
import com.springframework.csscapstone.payload.request_dto.collaborator.OrderCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.collaborator.OrderUpdaterDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.OrderResDto;
import com.springframework.csscapstone.services.OrderService;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.LackPointException;
import com.springframework.csscapstone.utils.exception_utils.order_exception.OrderNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.MapperDTO;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    @Override
    public OrderResDto getOrderResDtoById(UUID id) {
        return this.orderRepository.findById(id)
                .map(MapperDTO.INSTANCE::toOrderResDto)
                .orElseThrow(() -> new RuntimeException("No have Order With id: " + id));
    }

    @Override
    public PageImplResDto<OrderResDto> pageOrderOfCollaborator(
            UUID idCollaborator, OrderStatus orderStatus, Integer pageNumber, Integer pageSize) {

        Account account = this.accountRepository.findById(idCollaborator)
                .orElseThrow(() -> new EntityNotFoundException("The collaborator with id: " + idCollaborator + " not found"));

        pageNumber = Objects.isNull(pageNumber) || pageNumber < 1 ? 1 : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize < 1 ? 1 : pageSize;

        Page<Order> orders = this.orderRepository
                .pageOrderCollaboratorCreate(account, orderStatus, PageRequest.of(pageNumber - 1, pageSize));
        List<OrderResDto> content = orders
                .getContent()
                .stream()
                .map(MapperDTO.INSTANCE::toOrderResDto)
                .collect(Collectors.toList());

        return new PageImplResDto<>(content, orders.getNumber() + 1, content.size(),
                orders.getTotalElements(), orders.getTotalPages(), orders.isFirst(), orders.isLast());
    }
    @Transactional
    @Override
    public UUID createOrder(OrderCreatorReqDto dto) {

        Account account = this.accountRepository.findById(dto.getAccount().getAccountId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "The collaborator with id: " + dto.getAccount().getAccountId() + " not found"));

        Customer customer = this.customerRepository.findById(dto.getCustomer().getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "The customer with id: " + dto.getCustomer().getCustomerId() + " was not found"));

        //todo map<Product, quantity>
        Map<Product, Long> details = dto.getOrderDetails().stream()
                .collect(toMap(
                        od -> this.productRepository.findById(od.getProduct().getProductId())
                                .orElseThrow(handlerNotFoundException()),
                        OrderCreatorReqDto.OrderDetailInnerCreatorDto::getQuantity));

        List<OrderDetail> oderDetails = details.entrySet()
                .stream()
                .map(entry -> new OrderDetail(
                        entry.getKey().getName(),
                        entry.getKey().getPrice(),
                        entry.getKey().getPointSale(),
                        entry.getValue(),
                        entry.getValue() * entry.getKey().getPointSale(),
                        entry.getValue() * entry.getKey().getPrice())
                        .addProductToOrderDetail(entry.getKey()))
                .peek(this.orderDetailRepository::save)
                .collect(Collectors.toList());

        //todo get total price by map double with orderDetails
        double totalPrize = oderDetails
                .stream()
                .mapToDouble(OrderDetail::getTotalPointProduct)
                .sum();

        //todo get total point by map double with orderDetails
        double totalPointSale = oderDetails
                .stream()
                .map(OrderDetail::getTotalPriceProduct)
                .reduce(0.0, Double::sum);

        Order order = new Order(totalPrize, totalPointSale, customer.getName(),
                dto.getDeliveryPhone(), dto.getDeliveryAddress())
                .addOrderDetails(oderDetails)
                .addAccount(account)
                .addCustomer(customer);

        return this.orderRepository.save(order).getId();
    }
    @Transactional
    @Override
    public UUID updateOrder(OrderUpdaterDto dto) {
        Order waiting = this.orderRepository
                .findById(dto.idCollaborator)
                .filter(order -> order.getStatus().equals(OrderStatus.WAITING))
                .orElseThrow(() -> new RuntimeException("The Order not found or in processing so not allow to modified"));

        Customer customer = this.customerRepository
                .findById(dto.getCustomer().getCustomerId())
                .orElseThrow(() -> new RuntimeException("The customer not found"));
//        todo switch to order details to update quantity;
//        this.orderDetailRepository
//                        .findById(dto.getOrderDetails().get)

        waiting.setDeliveryAddress(dto.getDeliveryAddress())
                .setDeliveryPhone(dto.getDeliveryPhone())
                .setCustomer(customer);
        Order savedOrder = this.orderRepository.save(waiting);

        return savedOrder.getId();
    }
    //todo using for collaborator
    @Transactional
    @Override
    public void deleteOrder(UUID id) {
        Order order = this.orderRepository
                .findById(id)
                .filter(_order -> _order.getStatus().equals(OrderStatus.WAITING))
                .orElseThrow(() -> new RuntimeException("No have order by id: " + id + " or order in pending process"));

        this.orderRepository.save(order.setStatus(OrderStatus.DISABLE));

    }

    @Override
    public Optional<UUID> updateStatusOrder(UUID id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> handlerOrderNotFound().get());
        order.setStatus(status);
        this.orderRepository.save(order);
        return Optional.of(order.getId());
    }


    //todo close order to convert point to collaborators:

    /**
     * Order -> Order-Detail: (total point)
     * @param orderId
     */
    @Override
    public void completedOrder(UUID orderId) {
        Order order = this.orderRepository.findById(orderId)
                .filter(_order -> _order.getStatus() != OrderStatus.FINISH)
                .orElseThrow(() -> handlerOrderNotFound().get());

        Account collaborator = order.getAccount();

//        todo good code
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
            if (_enterprise.getPoint() < totalPoint) throw handlerLackPoint().get();

            _enterprise.setPoint(_enterprise.getPoint() - totalPoint);
            collaborator.setPoint(collaborator.getPoint() + totalPoint);
            this.accountRepository.save(collaborator);
            this.accountRepository.save(_enterprise);
        });
    }

    private Supplier<LackPointException> handlerLackPoint() {
        return () -> new LackPointException(MessagesUtils.getMessage(MessageConstant.Point.LACK_POINT));
    }

    private Supplier<OrderNotFoundException> handlerOrderNotFound() {
        return () -> new OrderNotFoundException(
                MessagesUtils.getMessage(MessageConstant.Order.NOT_FOUND));
    }
    private Supplier<EntityNotFoundException> handlerNotFoundException() {
        return () -> new EntityNotFoundException("The product in order detail was not found");
    }

}
