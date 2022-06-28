package com.springframework.csscapstone.controller.enterprise;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.status.OrderStatus;
import com.springframework.csscapstone.services.OrderService;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Order.V2_ORDER_COMPLETE;
import static com.springframework.csscapstone.config.constant.ApiEndPoint.Order.V2_ORDER_UPDATE_STATUS;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Order (Enterprise)")
public class EnterpriseOrderController {
    private final OrderService orderService;

    @PutMapping(V2_ORDER_UPDATE_STATUS + "/{orderId}")
    public ResponseEntity<?> updateStatusOrder(
            @PathVariable("orderId") UUID id,
            @RequestParam(value = "status", defaultValue = "PENDING") OrderStatus status) {
        Optional<UUID> uuid = this.orderService.updateStatusOrder(id, status);
        return ok(uuid);
    }

    @PutMapping(V2_ORDER_COMPLETE + "/{orderId}")
    public ResponseEntity<?> completeOrder(@PathVariable("orderId") UUID orderId) {
        this.orderService.completedOrder(orderId);
        return ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }

}
