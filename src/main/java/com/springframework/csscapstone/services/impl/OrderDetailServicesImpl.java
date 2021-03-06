package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.config.message.constant.MessageConstant;
import com.springframework.csscapstone.data.domain.Order;
import com.springframework.csscapstone.data.domain.OrderDetail;
import com.springframework.csscapstone.data.domain.Product;
import com.springframework.csscapstone.data.repositories.OrderDetailRepository;
import com.springframework.csscapstone.data.repositories.OrderRepository;
import com.springframework.csscapstone.data.repositories.ProductRepository;
import com.springframework.csscapstone.services.OrderDetailService;
import com.springframework.csscapstone.payload.request_dto.admin.OrderDetailCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.admin.OrderDetailUpdaterReqDto;
import com.springframework.csscapstone.utils.exception_utils.order_detail_exception.OrderDetailException;
import com.springframework.csscapstone.utils.exception_utils.order_detail_exception.ProductCanCreateException;
import com.springframework.csscapstone.utils.exception_utils.order_exception.OrderNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class OrderDetailServicesImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;


    @Override
    public List<OrderDetail> findAll() {
        return null;
    }

    @Override
    public OrderDetail findById(UUID id) {
        return null;
    }

    @Transactional
    @Override
    public UUID createOrderDetail(OrderDetailCreatorReqDto dto) throws ProductNotFoundException, OrderNotFoundException, ProductCanCreateException, OrderDetailException {
        Product product = this.productRepository.findById(dto.getIdProduct()).orElseThrow(productException);

        Order order = this.orderRepository.findById(dto.getOrder().getId()).orElseThrow(orderDetailNotFoundException);


        OrderDetail orderDetail = Optional
                .of(new OrderDetail()).map(x -> creatorUtils(dto, order, product, x))
                .orElseThrow(() -> new ProductCanCreateException(MessagesUtils.getMessage(MessageConstant.OrderDetail.CANT_CREATE)));

        OrderDetail savedOrderDetail = this.orderDetailRepository.save(orderDetail);
        Order savedOrder = this.orderRepository.save(order);

        return savedOrderDetail.getId();
    }

    private OrderDetail creatorUtils(OrderDetailCreatorReqDto dto, Order order, Product product, OrderDetail detail) {
        //calculate the total price of product
        double priceLineOfProduct = dto.getQuantity() * product.getPrice();

        detail.setNameProduct(product.getName())
                .setQuantity(dto.getQuantity())
                .setProductPrice(priceLineOfProduct)
                .addOrderDetailDToOrder(order);
        return detail;
    }

    private final Supplier<OrderDetailException> orderDetailNotFoundException =
            () -> new OrderDetailException(MessagesUtils.getMessage(MessageConstant.Order.NOT_FOUND));

    //============================Method Utils ==========================
    private final Supplier<ProductNotFoundException> productException =
            () -> new ProductNotFoundException(MessagesUtils.getMessage(MessageConstant.Product.NOT_FOUND));

    @Transactional
    @Override
    public UUID updateOrderDetail(UUID id, OrderDetailUpdaterReqDto dto) throws OrderDetailException, ProductNotFoundException {
        OrderDetail orderDetail = this.orderDetailRepository.findById(id)
                .filter(x -> x.getId().equals(id))
                .orElseThrow(() -> new OrderDetailException(MessagesUtils.getMessage(MessageConstant.OrderDetail.NOT_FOUND)));
        Product product = this.productRepository.findById(dto.getIdProduct()).orElseThrow(productException);
        orderDetail.setNameProduct(product.getName())
                .setQuantity(dto.getQuantity())
                .setProductPrice(product.getPrice() * dto.getQuantity());
        this.orderDetailRepository.save(orderDetail);
        return orderDetail.getId();
    }

    @Transactional
    @Override
    public void delete(UUID id) throws OrderDetailException {
        //detach order detail with order;
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(orderDetailNotFoundException);
        OrderDetail _remove = orderDetail.removeOrderDetailsFromOrder();

        this.orderDetailRepository.delete(_remove);
    }
}
