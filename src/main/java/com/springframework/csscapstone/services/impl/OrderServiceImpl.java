package com.springframework.csscapstone.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springframework.csscapstone.config.firebase_config.FirebaseMessageService;
import com.springframework.csscapstone.config.firebase_config.model.PushNotificationRequest;
import com.springframework.csscapstone.config.message.constant.MessageConstant;
import com.springframework.csscapstone.config.message.constant.MobileScreen;
import com.springframework.csscapstone.data.dao.specifications.OrdersSpecification;
import com.springframework.csscapstone.data.domain.*;
import com.springframework.csscapstone.data.repositories.*;
import com.springframework.csscapstone.data.status.OrderStatus;
import com.springframework.csscapstone.payload.request_dto.collaborator.OrderCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.collaborator.OrderUpdaterDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.EnterpriseRevenueDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.OrderEnterpriseManageResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.OrderResDto;
import com.springframework.csscapstone.services.OrderService;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.LackPointException;
import com.springframework.csscapstone.utils.exception_utils.order_exception.OrderNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.MapperDTO;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.springframework.csscapstone.utils.exception_catch_utils.ExceptionFCMHandler.fcmException;
import static java.util.stream.Collectors.toMap;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private static final double priceLatch = 0.0;
    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final AccountTokenRepository accountTokenRepository;
    private final FirebaseMessageService firebaseMessageService;
    private final int INVALID_PAGE = 1;
    private final int DEFAULT_PAGE_NUMBER = 1;
    private final int SHIFT_TO_ACTUAL_PAGE = 1;
    private final int DEFAULT_PAGE_SIZE = 10;
    private final Supplier<RuntimeException> notFoundOrderException =
            () -> new RuntimeException("The Order not found or in processing so not allow to modified");
    private final Function<UUID, Supplier<RuntimeException>> notFoundOrderWithIdException =
            (id) -> () -> new RuntimeException("No have order by id: " + id + " or order in pending process");
    private final Supplier<LackPointException> handlerLackPoint =
            () -> new LackPointException(MessagesUtils.getMessage(MessageConstant.Point.LACK_POINT));
    private final Supplier<OrderNotFoundException> handlerOrderNotFound =
            () -> new OrderNotFoundException(MessagesUtils.getMessage(MessageConstant.Order.NOT_FOUND));
    private final Supplier<EntityNotFoundException> handlerNotFoundException =
            () -> new EntityNotFoundException("The product in order detail was not found");
    private final Supplier<RuntimeException> notSameEnterpriseException =
            () -> new RuntimeException("Product not have same id enterprise!!!");
    private final Supplier<RuntimeException> customerNotFoundException =
            () -> new RuntimeException("The Customer Not Found!!!");
    private final Function<UUID, Supplier<EntityNotFoundException>> collaboratorNotFoundException =
            (id) -> () -> new EntityNotFoundException("The collaborator with id: " + id + " not found");
    private final Function<UUID, Predicate<UUID>> isSameEnterpriseId = (id) -> (enterpriseId) -> !enterpriseId.equals(id);
    private final Function<UUID, Supplier<RuntimeException>> orderNotFound = (id) -> () -> new RuntimeException("No have Order With id: " + id);

    private void clearCache() {

    }

    @Override
    @Cacheable(key = "#p0", value = "getOrderResDtoById")
    public OrderResDto getOrderResDtoById(UUID id) {
        return this.orderRepository.findById(id)
                .filter(order -> !order.getStatus().equals(OrderStatus.DISABLED))
                .map(MapperDTO.INSTANCE::toOrderResDto)
                .orElseThrow(orderNotFound.apply(id));
    }

    @Override
    @Cacheable(key = "{#p0, #p1, #p2, #p3}", value = "pageOrderOfCollaborator")
    public PageImplResDto<OrderResDto> pageOrderOfCollaborator(
            UUID idCollaborator, OrderStatus orderStatus, Integer pageNumber, Integer pageSize) {

        Account account = this.accountRepository.findById(idCollaborator)
                .orElseThrow(collaboratorNotFoundException.apply(idCollaborator));


        pageNumber = Objects.isNull(pageNumber) || pageNumber < INVALID_PAGE ? DEFAULT_PAGE_NUMBER : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize < INVALID_PAGE ? DEFAULT_PAGE_SIZE : pageSize;

        Specification<Order> conditions = Specification
                .where(Objects.isNull(orderStatus) ? null : OrdersSpecification.equalsStatus(orderStatus))
                .and(OrdersSpecification.equalsCollaborator(account))
                .and(OrdersSpecification.excludeDisableStatus());

        Page<Order> orders = this.orderRepository
                .findAll(conditions,
                        PageRequest.of(pageNumber - SHIFT_TO_ACTUAL_PAGE, pageSize,
                                Sort.by(Order_.CREATE_DATE).descending()));

        List<OrderResDto> content = orders
                .getContent()
                .stream()
                .map(MapperDTO.INSTANCE::toOrderResDto)
                .collect(Collectors.toList());

        return new PageImplResDto<>(content, orders.getNumber() + SHIFT_TO_ACTUAL_PAGE, content.size(),
                orders.getTotalElements(), orders.getTotalPages(), orders.isFirst(), orders.isLast());
    }

    @Transactional
    @Override
    public UUID createOrder(OrderCreatorReqDto orderCreatorDto) {
        Account account = this.accountRepository
                .findById(orderCreatorDto.getAccount().getAccountId())
                .orElseThrow(notFoundOrderWithIdException.apply(orderCreatorDto.getAccount().getAccountId()));

        Customer customer = this.customerRepository
                .findById(orderCreatorDto.getCustomer().getCustomerId())
                .orElseThrow(customerNotFoundException);

        //check field
        orderCreatorDto.getOrderDetails()
                .stream()
                .map(OrderCreatorReqDto.OrderDetailInnerCreatorDto::getProduct)
                .map(OrderCreatorReqDto.OrderDetailInnerCreatorDto.ProductDto::getProductId)
                .filter(isSameEnterpriseId.apply(account.getId()))
                .findAny()
                .orElseThrow(notSameEnterpriseException);

        //todo map<Product, quantity>
        Map<Product, Long> quantityProducts = orderCreatorDto.getOrderDetails().stream()
                .collect(toMap(
                        od -> this.productRepository
                                .findById(od.getProduct().getProductId())
                                .orElseThrow(handlerNotFoundException),
                        OrderCreatorReqDto.OrderDetailInnerCreatorDto::getQuantity));

        List<OrderDetail> oderDetails = quantityProducts.entrySet()
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
        double totalPrice = oderDetails
                .stream()
                .mapToDouble(OrderDetail::getTotalPriceProduct)
                .sum();

        //todo get total point by map double with orderDetails

        double totalPointSale = oderDetails
                .stream()
                .map(OrderDetail::getTotalPointProduct)
                .reduce(priceLatch, Double::sum);

        Order order = new Order(totalPrice, totalPointSale, customer.getName(),
                orderCreatorDto.getDeliveryPhone(), orderCreatorDto.getDeliveryAddress())
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
                .orElseThrow(notFoundOrderException);

        Customer customer = this.customerRepository
                .findById(dto.getCustomer().getCustomerId())
                .orElseThrow(customerNotFoundException);

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
//                .filter(_order -> _order.getStatus().equals(OrderStatus.WAITING))
                .orElseThrow(notFoundOrderWithIdException.apply(id));
        this.orderRepository.save(order.setStatus(OrderStatus.DISABLED));

    }

    @Override
    @Transactional
//    @Cacheable(key = "{#p0, #p1}", value = "updateStatusOrder")
    public Optional<UUID> updateStatusOrder(UUID id, OrderStatus status) {
        Order order = orderRepository.findById(id).orElseThrow(handlerOrderNotFound);
        order.setStatus(status);
        this.orderRepository.save(order);
        return Optional.of(order.getId());
    }

    //todo close order to convert point to collaborators:

    /**
     * Order -> Order-Detail: (total point)
     * todo Notification to collaborators:
     * @param orderId
     */
    @Override
    @Transactional
    public void completedOrder(UUID orderId) {

        //check order valid
        Order order = this.orderRepository.findById(orderId)
                .filter(_order -> _order.getStatus() != OrderStatus.FINISHED)
                .orElseThrow(handlerOrderNotFound);

        //get collaborator who create order
        Account collaborator = order.getAccount();

//        todo good code
//        Double totalPoint = order.getOrderDetails()
//                .stream()
//                .map(OrderDetail::getTotalPointProduct)
//                .mapToDouble(Double::doubleValue)
//                .sum();

        //get total point in order details
        Double totalPoint = order.getOrderDetails()
                .stream()
                .map(OrderDetail::getTotalPointProduct)
                .reduce(priceLatch, Double::sum);

        //todo find enterprise:
        List<Account> enterprises = order.getOrderDetails()
                .stream()
                .map(detail -> detail.getProduct().getAccount())
                .distinct().collect(Collectors.toList());

        if (enterprises.size() > 1) {
            throw new RuntimeException("Order wrong with 2 enterprise!!!");
        }

        //todo point of enterprise must be large enough
        Account enterprise = enterprises.get(0);
        Optional.of(enterprise).ifPresent(_enterprise -> {
            if (_enterprise.getPoint() < totalPoint) throw handlerLackPoint.get();

            _enterprise.setPoint(_enterprise.getPoint() - totalPoint);
            collaborator.setPoint(collaborator.getPoint() + totalPoint);
            this.accountRepository.save(collaborator);
            this.accountRepository.save(_enterprise);
        });

        //todo send notification
        this.orderRepository.save(order.setStatus(OrderStatus.FINISHED));

        //send notification:
        /**
         * Customer name, enterprise, datetime order, total point inscrease
         *
         */
        //todo get account token:
        accountTokenRepository.getAccountTokenByAccountOptional(order.getAccount().getId())
                .ifPresent(fcmException(token ->  sendNotificationToCollaborator(
                        order.getCustomer().getName(), enterprise.getName(),
                        enterprise.getAvatar().getPath(),
                        order.getId(), order.getCreateDate(), order.getTotalPointSale(),
                        token.get(0).getRegistrationToken())));

    }

    private void sendNotificationToCollaborator(
            String customerName, String enterpriseName, String path, UUID orderId, LocalDateTime createDate, Double totalPointSale, String token) throws ExecutionException, JsonProcessingException, InterruptedException {
        HashMap<String, String> data = new HashMap<>();
        data.put(MobileScreen.SCREEN.getScreen(), MobileScreen.ORDER_DETAIL.getScreen());
        data.put(MobileScreen.INFO.getScreen(), orderId.toString());

        String title = "Your point increase " + totalPointSale + " by " + enterpriseName;
        String message = "The order belong to " + customerName + " at time: " + createDate + " was completed";
        String topic = "The point increase";

        firebaseMessageService.sendMessage(data, new PushNotificationRequest(title, message, topic, token, path));

    }

    @Override
    @Cacheable(key = "{#p0, #p1, #p2}", value = "getOrderResDtoByEnterprise")
    public PageImplResDto<OrderEnterpriseManageResDto> getOrderResDtoByEnterprise(
            UUID enterpriseId, Integer pageNumber, Integer pageSize) {

        pageNumber = Objects.isNull(pageNumber) || pageNumber < INVALID_PAGE ? DEFAULT_PAGE_NUMBER : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize < INVALID_PAGE ? DEFAULT_PAGE_SIZE : pageSize;

        Page<Order> page = this.orderRepository.getOrderByEnterprise(enterpriseId, OrderStatus.DISABLED, PageRequest.of(
                pageNumber - SHIFT_TO_ACTUAL_PAGE, pageSize,
                Sort.by(Order_.CREATE_DATE).descending()));

        List<OrderEnterpriseManageResDto> content = page.getContent()
                .stream()
                .map(MapperDTO.INSTANCE::toOrderEnterpriseManageResDto)
                .collect(Collectors.toList());


        return new PageImplResDto<>(content, page.getNumber() + SHIFT_TO_ACTUAL_PAGE, content.size(),
                page.getTotalElements(), page.getTotalPages(),
                page.isFirst(), page.isLast());
    }

    @Override
    @Cacheable(key = "#p0", value = "getRevenue")
    public Optional<List<EnterpriseRevenueDto>> getRevenue(UUID enterpriseId) {
        List<EnterpriseRevenueDto> revenueDtos = this.orderRepository
                .getRevenueByEnterprise(enterpriseId)
                .stream()
                .collect(
                        toMap(tuple -> tuple.get(OrderRepository.ORDER_DATE, Integer.class),
                                tuple -> tuple.get(OrderRepository.ORDER_REVENUE, Double.class)))
                .entrySet()
                .stream()
                .map(entry -> new EnterpriseRevenueDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return Optional.of(revenueDtos);
    }
}
