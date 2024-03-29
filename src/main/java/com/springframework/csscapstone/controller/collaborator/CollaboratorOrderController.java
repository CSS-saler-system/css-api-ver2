package com.springframework.csscapstone.controller.collaborator;

import com.springframework.csscapstone.config.message.constant.MessageConstant;
import com.springframework.csscapstone.data.status.OrderStatus;
import com.springframework.csscapstone.payload.request_dto.collaborator.OrderCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.collaborator.OrderUpdaterDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.OrderResDto;
import com.springframework.csscapstone.services.OrderService;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.Order.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Order (Collaborator)")
public class CollaboratorOrderController {
    private final OrderService orderService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @GetMapping(V3_ORDER_LIST + "/{collaboratorId}")
    public ResponseEntity<?> getPageOrderOfCollaborator(
            @PathVariable("collaboratorId") UUID idCollaborator,
            @RequestParam(value = "status", required = false) OrderStatus status,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize
    ) {
        LOGGER.info("The status {}", status);
        PageImplResDto<OrderResDto> page = this.orderService
                .pageOrderOfCollaborator(idCollaborator, status, pageNumber, pageSize);
        return ok(page);
    }

    @PostMapping(V3_ORDER_CREATE)
    public ResponseEntity<?> createOrder(@RequestBody OrderCreatorReqDto dto) {
        UUID order = this.orderService.createOrder(dto);
        return ok(order);
    }

    @GetMapping(V3_ORDER_GET + "/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable("orderId") UUID id) {
        return ok(this.orderService.getOrderResDtoById(id));
    }

    @PutMapping(V3_ORDER_UPDATE)
    public ResponseEntity<?> updateOrderById(
            @RequestBody OrderUpdaterDto orderUpdaterDto) {
        UUID uuid = this.orderService.updateOrder(orderUpdaterDto);
        return ok(uuid);
    }

    @DeleteMapping(V3_ORDER_DELETE + "/{orderId}")
    public ResponseEntity<?> deleteOrderById(
            @PathVariable("orderId") UUID id
    ) {
        this.orderService.deleteOrder(id);
        return ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }


}
