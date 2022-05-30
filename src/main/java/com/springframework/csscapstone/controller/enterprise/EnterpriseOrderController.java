package com.springframework.csscapstone.controller.enterprise;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.status.OrderStatus;
import com.springframework.csscapstone.services.OrderService;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
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
public class EnterpriseOrderController {
    private final OrderService orderService;

    @PutMapping(V2_ORDER_UPDATE_STATUS + "/{id}")
    public ResponseEntity<?> updateStatusOrder(
            @PathVariable("id") UUID id,
            @RequestParam("status")OrderStatus status
            ) {
        Optional<UUID> uuid = this.orderService.updateOrder(id, status);
        return ok(uuid);
    }

    @PutMapping(V2_ORDER_COMPLETE + "/{id}")
    public ResponseEntity<?> completeOrder(@PathVariable("id") UUID id) {
        this.orderService.completedOrder(id);
        return ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }

}
