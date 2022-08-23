package com.springframework.csscapstone.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import com.springframework.csscapstone.payload.response_dto.enterprise.OrderChartEnterpriseResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.OrderEnterpriseManageResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.OrderResDto;
import com.springframework.csscapstone.services.OrderService;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.LackPointException;
import com.springframework.csscapstone.utils.exception_utils.order_exception.OrderNotFoundException;
import com.springframework.csscapstone.utils.fcm_utils.FirebaseMessageAsyncUtils;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.AccountImageMapper;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.MapperDTO;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
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
    private final int INVALID_PAGE = 1;
    private final int DEFAULT_PAGE_NUMBER = 1;
    private final int SHIFT_TO_ACTUAL_PAGE = 1;
    private final int DEFAULT_PAGE_SIZE = 10;
    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final AccountTokenRepository accountTokenRepository;
    private final FirebaseMessageAsyncUtils firebaseMessageAsyncUtils;
    private final Supplier<RuntimeException> notFoundOrderException =
            () -> new RuntimeException(MessagesUtils.getMessage(MessageConstant.Order.ORDER_NOT_FOUND_OR_PROCESSING));
    private final Function<UUID, Supplier<RuntimeException>> notFoundOrderWithIdException =
            (id) -> () -> new RuntimeException(MessagesUtils.getMessage(MessageConstant.Order.ORDER_NOT_FOUND_WITH_ID) + id);
    private final Supplier<LackPointException> handlerLackPoint =
            () -> new LackPointException(MessagesUtils.getMessage(MessageConstant.Point.LACK_POINT));
    private final Supplier<OrderNotFoundException> handlerOrderNotFound =
            () -> new OrderNotFoundException(MessagesUtils.getMessage(MessageConstant.Order.NOT_FOUND));
    private final Supplier<EntityNotFoundException> handlerNotFoundException =
            () -> new EntityNotFoundException(MessagesUtils.getMessage(MessageConstant.OrderDetail.NOT_HAVE_PRODUCT_IN_ORDER_DETAIL));
    private final Supplier<RuntimeException> notSameEnterpriseException =
            () -> new RuntimeException(MessagesUtils.getMessage(MessageConstant.Product.ERROR_NOT_SAME_ID_ENTERPRISE));
    private final Supplier<RuntimeException> customerNotFoundException =
            () -> new RuntimeException(MessagesUtils.getMessage(MessageConstant.Customer.NOT_FOUND));
    private final Function<UUID, Supplier<EntityNotFoundException>> collaboratorNotFoundException =
            (id) -> () -> new EntityNotFoundException(MessagesUtils.getMessage(MessageConstant.Account.NOT_FOUND_WITH_ID) + id);
    private final Function<UUID, Predicate<UUID>> isSameEnterpriseId = (id) -> (enterpriseId) -> !enterpriseId.equals(id);
    private final Function<UUID, Supplier<RuntimeException>> orderNotFound =
            (id) -> () -> new RuntimeException(MessagesUtils.getMessage(MessageConstant.Order.ORDER_NOT_FOUND_WITH_ID) + id);
    private final CacheManager cacheManager;

    private void clearCache() {
        Objects.requireNonNull(cacheManager.getCache("getOrderResDtoById")).clear();
        Objects.requireNonNull(cacheManager.getCache("pageOrderOfCollaborator")).clear();
        Objects.requireNonNull(cacheManager.getCache("getOrderResDtoByEnterprise")).clear();
        Objects.requireNonNull(cacheManager.getCache("getRevenue")).clear();
    }

    @Override
    @Cacheable(key = "#p0", value = "getOrderResDtoById")
    public OrderResDto getOrderResDtoById(UUID id) {
        return this.orderRepository.findById(id)
                .filter(order -> !order.getStatus().equals(OrderStatus.DISABLED))
                .map(getOrderOrderResDtoFunction())
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
                .findAll(conditions, PageRequest.of(pageNumber - SHIFT_TO_ACTUAL_PAGE, pageSize,
                        Sort.by(Order_.CREATE_DATE).descending()));

        List<OrderResDto> content = orders
                .getContent()
                .stream()
                .map(getOrderOrderResDtoFunction())
                .collect(Collectors.toList());

        return new PageImplResDto<>(content, orders.getNumber() + SHIFT_TO_ACTUAL_PAGE, content.size(),
                orders.getTotalElements(), orders.getTotalPages(), orders.isFirst(), orders.isLast());
    }

    private static Function<Order, OrderResDto> getOrderOrderResDtoFunction() {
        int firstElement = 0;
        return order -> {
            AccountImage avatar = order.getOrderDetails().get(firstElement).getProduct().getAccount().getAvatar();
            AccountImageBasicVer2Dto image = AccountImageMapper.INSTANCE.accountImageToAccountImageBasicVer2Dto(avatar);
            return MapperDTO.INSTANCE.toOrderResDto(order, image);
        };
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
                        Math.ceil(entry.getValue() * entry.getKey().getPointSale()),
                        Math.ceil(entry.getValue() * entry.getKey().getPrice())).addProductToOrderDetail(entry.getKey()))
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
        clearCache();
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
        clearCache();
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
        clearCache();
    }

    @Override
    @Transactional
    public Optional<UUID> updateStatusOrder(UUID id, OrderStatus status) {
        Order order = orderRepository.findById(id).orElseThrow(handlerOrderNotFound);
        order.setStatus(status);
        this.orderRepository.save(order);

        clearCache();

        return Optional.of(order.getId());
    }

    //todo close order to convert point to collaborators:

    /**
     * Order -> Order-Detail: (total point)
     * todo Notification to collaborators:
     *
     * @param orderId
     */
    @Override
    @Transactional
    public void completedOrder(UUID orderId) {
        int firstElement = 0;
        //check order valid
        Order order = this.orderRepository.findById(orderId)
                .filter(_order -> _order.getStatus() != OrderStatus.FINISHED)
                .filter(_order -> _order.getStatus() != OrderStatus.DISABLED)
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
        if (enterprises.size() != 1) {
            throw new RuntimeException(MessagesUtils.getMessage(MessageConstant.Order.NOT_HAVE_ENTERPRISE));
        }

        //todo point of enterprise must be large enough

        Account enterprise = enterprises.get(firstElement);
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
         */
        //todo get account token:
        accountTokenRepository.getAccountTokenByAccountOptional(order.getAccount().getId())
                .ifPresent(fcmException(token -> sendNotificationToCollaborator(
                        order.getCustomer().getName(), enterprise.getName(),
                        enterprise.getAvatar().getPath(),
                        order.getId(), order.getCreateDate(), order.getTotalPointSale(),
                        token.get(firstElement).getRegistrationToken())));
        clearCache();
    }

    private void sendNotificationToCollaborator(
            String customerName, String enterpriseName, String path, UUID orderId, LocalDateTime createDate, Double totalPointSale, String token) throws ExecutionException, JsonProcessingException, InterruptedException {
        HashMap<String, String> data = new HashMap<>();
        data.put(MobileScreen.SCREEN.getScreen(), MobileScreen.ORDER_DETAIL.getScreen());
        data.put(MobileScreen.INFO.getScreen(), orderId.toString());

        String title = "Your point increase " + totalPointSale + " by " + enterpriseName;
        String message = "The order belong to " + customerName + " at time: " + createDate + " was completed";
        String topic = "The point increase";

        firebaseMessageAsyncUtils.sendMessage(data, new PushNotificationRequest(title, message, topic, token, path));

    }

    @Override
    @Cacheable(key = "{#p0, #p1, #p2, #p3}", value = "getOrderResDtoByEnterprise")
    public PageImplResDto<OrderEnterpriseManageResDto> getOrderResDtoByEnterprise(
            UUID enterpriseId, OrderStatus orderStatus, Integer pageNumber, Integer pageSize) {

        pageNumber = Objects.isNull(pageNumber) || pageNumber < INVALID_PAGE ? DEFAULT_PAGE_NUMBER : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize < INVALID_PAGE ? DEFAULT_PAGE_SIZE : pageSize;

//        Page<Order> page = this.orderRepository
//                .getOrderByEnterprise(enterpriseId, OrderStatus.DISABLED, PageRequest.of(
//                pageNumber - SHIFT_TO_ACTUAL_PAGE, pageSize, Sort.by(Order_.CREATE_DATE).descending()));
        Specification<Order> conditions = Specification
                .where(Objects.isNull(orderStatus) ? null : OrdersSpecification.equalsStatus(orderStatus))
//                .and(OrdersSpecification.equalsCollaborator(account))
                .and(OrdersSpecification.equalsEnterpriseId(enterpriseId))
                .and(OrdersSpecification.excludeDisableStatus());

        Page<Order> page = this.orderRepository
                .findAll(conditions, PageRequest.of(
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
                .collect(toMap(
                                tuple -> tuple.get(OrderRepository.ORDER_DATE, Integer.class),
                                tuple -> tuple.get(OrderRepository.ORDER_REVENUE, Double.class)))
                .entrySet()
                .stream()
                .map(entry -> new EnterpriseRevenueDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        return Optional.of(revenueDtos);
    }

    @Override
    public Map<String, OrderChartEnterpriseResDto> getTotalOrderByEnterpriseId(UUID enterpriseId) {
        Map<Integer, Long> totalOrder = this.orderRepository.getOrderByMonth(enterpriseId)
                .stream().collect(toMap(
                        tuple -> tuple.get(OrderRepository.ORDER_BY_MONTH, Integer.class),
                        tuple -> tuple.get(OrderRepository.ORDER_TOTAL, Number.class).longValue()));

        Map<Integer, Long> finishedOrder = this.orderRepository.getOrderByMonthWithStatus(enterpriseId, OrderStatus.FINISHED)
                .stream().collect(toMap(
                        tuple -> tuple.get(OrderRepository.ORDER_BY_MONTH, Integer.class),
                        tuple -> tuple.get(OrderRepository.ORDER_TOTAL, Number.class).longValue()));

        Map<Integer, Long> cancelOrder = this.orderRepository.getOrderByMonthWithStatus(enterpriseId, OrderStatus.CANCELED)
                .stream().collect(toMap(
                        tuple -> tuple.get(OrderRepository.ORDER_BY_MONTH, Integer.class),
                        tuple -> tuple.get(OrderRepository.ORDER_TOTAL, Number.class).longValue()));

        HashMap<String, OrderChartEnterpriseResDto> res = new HashMap<>();

        OrderChartEnterpriseResDto _totalOrder = handlerOrderQuantityWithMonth(totalOrder);
        OrderChartEnterpriseResDto _finishedOrder = handlerOrderQuantityWithMonth(finishedOrder);
        OrderChartEnterpriseResDto _cancelOrder = handlerOrderQuantityWithMonth(cancelOrder);

        res.put("total_order", _totalOrder);
        res.put("finish_order", _finishedOrder);
        res.put("cancel_order", _cancelOrder);

        return res;
    }

    private OrderChartEnterpriseResDto handlerOrderQuantityWithMonth(Map<Integer, Long> preTotalOrder) {
        OrderChartEnterpriseResDto orderChartEnterpriseResDto = new OrderChartEnterpriseResDto();
        List<Long> collect = preTotalOrder.keySet()
                .stream()
                .map(key -> orderChartEnterpriseResDto.getQuantity()[key - 1] = preTotalOrder.get(key))
                .collect(Collectors.toList());
        return orderChartEnterpriseResDto;
    }
}
