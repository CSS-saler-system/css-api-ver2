package com.springframework.csscapstone.controller.collaborator;

import com.springframework.csscapstone.services.OrderDetailService;
import com.springframework.csscapstone.payload.custom.creator_model.OrderDetailCreator;
import com.springframework.csscapstone.payload.custom.update_model.OrderDetailUpdater;
import com.springframework.csscapstone.utils.exception_utils.order_detail_exception.OrderDetailException;
import com.springframework.csscapstone.utils.exception_utils.order_detail_exception.ProductCanCreateException;
import com.springframework.csscapstone.utils.exception_utils.order_exception.OrderNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.MapperDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.OrderDetail.*;
import static com.springframework.csscapstone.config.constant.MessageConstant.REQUEST_SUCCESS;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class CollaboratorOrderController {
    private final OrderDetailService orderDetailService;

    @GetMapping(V2_GET_ORDER_DETAIL + "{id}")
    public ResponseEntity<?> getOrderDetail(@PathVariable UUID id) {
      return ok(MapperDTO.INSTANCE.toOrderDetailDto(orderDetailService.findById(id)));
    }

    @PostMapping(V2_CREATE_ORDER_DETAIL)
    public ResponseEntity<?> createOrderDetail(@RequestBody OrderDetailCreator dto) throws OrderNotFoundException, ProductCanCreateException, OrderDetailException, ProductNotFoundException {
        UUID id = this.orderDetailService.createOrderDetail(dto);
        return ok(id);
    }

    @PutMapping(V2_UPDATE_ORDER_DETAIL + "{id}")
    public ResponseEntity<?> updateOrderDetail(@PathVariable UUID id, @RequestBody OrderDetailUpdater dto) throws OrderDetailException, ProductNotFoundException {
        UUID uuid = this.orderDetailService.updateOrderDetail(id, dto);
        return ok(id);
    }

    @GetMapping(V2_LIST_ORDER_DETAIL)
    public ResponseEntity<?> getListOrderDetails() {
        return ok(this.orderDetailService.findAll());
    }

    @DeleteMapping(V2_DELETE_ORDER_DETAIL)
    public ResponseEntity<?> deleteOrderDetail(@PathVariable("id") UUID id) throws OrderDetailException {
        this.orderDetailService.delete(id);
        return ok(REQUEST_SUCCESS);
    }

}
