package com.springframework.csscapstone.controller.collaborator;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.status.OrderStatus;
import com.springframework.csscapstone.payload.request_dto.collaborator.OrderCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.collaborator.OrderUpdaterDto;
import com.springframework.csscapstone.payload.response_dto.PageImplResDto;
import com.springframework.csscapstone.payload.response_dto.enterprise.OrderResDto;
import com.springframework.csscapstone.services.OrderService;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Order.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Order (Collaborator)")
public class CollaboratorOrderController {
    private final OrderService orderService;

    @GetMapping(V3_ORDER_LIST + "/{collaboratorId}")
    public ResponseEntity<?> getPageOrderOfCollaborator(
            @PathVariable("collaboratorId") UUID idCollaborator,
            @RequestParam(value = "status", required = false, defaultValue = "WAITING") OrderStatus status,
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "1") Integer pageSize
    ) {
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

    @DeleteMapping(V3_ORDER_DELETE + "{orderId}")
    public ResponseEntity<?> deleteOrderById(
            @PathVariable("orderId") UUID id
    ) {
        this.orderService.deleteOrder(id);
        return ok(MessagesUtils.getMessage(MessageConstant.REQUEST_SUCCESS));
    }


}
