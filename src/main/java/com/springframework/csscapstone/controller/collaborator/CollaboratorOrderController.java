package com.springframework.csscapstone.controller.collaborator;

import com.springframework.csscapstone.payload.request_dto.collaborator.OrderCreatorDto;
import com.springframework.csscapstone.services.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Order.V3_ORDER_CREATE;
import static com.springframework.csscapstone.config.constant.ApiEndPoint.Order.V3_ORDER_GET;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Order (Collaborator)")
public class CollaboratorOrderController {
    private final OrderService orderService;

    @PostMapping(V3_ORDER_CREATE)
    public ResponseEntity<?> createOrder(@RequestBody OrderCreatorDto dto) {
        UUID order = this.orderService.createOrder(dto);
        return ok(order);
    }

    @GetMapping(V3_ORDER_GET + "/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") UUID id) {
        return ok(this.orderService.getOrderResDtoById(id));
    }

}
