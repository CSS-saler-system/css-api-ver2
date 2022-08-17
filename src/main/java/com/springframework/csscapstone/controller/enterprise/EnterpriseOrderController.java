package com.springframework.csscapstone.controller.enterprise;

import com.springframework.csscapstone.config.message.constant.MessageConstant;
import com.springframework.csscapstone.data.status.OrderStatus;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.OrderEnterpriseManageResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.OrderResDto;
import com.springframework.csscapstone.services.OrderService;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.Order.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Order (Enterprise)")
public class EnterpriseOrderController {
    private final OrderService orderService;

    @PutMapping(V2_ORDER_UPDATE_STATUS + "/{orderId}")
    public ResponseEntity<?> updateStatusOrder(
            @PathVariable("orderId") UUID id,
            @RequestParam(value = "status", defaultValue = "PROCESSING") OrderStatus status) {
        Optional<UUID> uuid = this.orderService.updateStatusOrder(id, status);
        return ok(uuid);
    }

    @PutMapping(V2_ORDER_COMPLETE + "/{orderId}")
    public ResponseEntity<?> completeOrder(@PathVariable("orderId") UUID orderId) {
        this.orderService.completedOrder(orderId);
        return ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }

    @GetMapping(V2_ORDER_LIST_BY_ENTERPRISE + "/{enterpriseId}")
    public ResponseEntity<?> getAllOrderByEnterprise(
            @PathVariable("enterpriseId") UUID enterpriseId,
            @RequestParam(value = "status", required = false) OrderStatus orderStatus,
            @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        PageImplResDto<OrderEnterpriseManageResDto> result = this.orderService
                .getOrderResDtoByEnterprise(enterpriseId, orderStatus,pageNumber, pageSize);

        return ok(result);
    }


    @GetMapping(V2_ORDER_GET + "/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable("orderId") UUID orderId) {
        OrderResDto result = this.orderService.getOrderResDtoById(orderId);
        return ok(result);
    }

//    @GetMapping(V2_ORDER_TOTAL + "/{enterpriseId}")
//    public ResponseEntity<?> getTotalOrderByIdEnterprise(@PathVariable("enterpriseId") UUID enterpriseId) {
//        this.orderService.getTotalOrderByEnterpriseId(enterpriseId);
//    }
//    @GetMapping(V2_ORDER_SUCCESS + "/{enterpriseId}")
//    public ResponseEntity<?> getTotalOrderByIdEnterprise(@PathVariable("enterpriseId") UUID enterpriseId) {
//
//    }

}
