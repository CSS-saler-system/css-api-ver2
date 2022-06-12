package com.springframework.csscapstone.controller.collaborator;

import com.springframework.csscapstone.services.OrderDetailService;
import com.springframework.csscapstone.payload.request_dto.admin.OrderDetailCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.admin.OrderDetailUpdaterReqDto;
import com.springframework.csscapstone.utils.exception_utils.order_detail_exception.OrderDetailException;
import com.springframework.csscapstone.utils.exception_utils.order_detail_exception.ProductCanCreateException;
import com.springframework.csscapstone.utils.exception_utils.order_exception.OrderNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.product_exception.ProductNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.dto_mapper.MapperDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.OrderDetail.*;
import static com.springframework.csscapstone.config.constant.MessageConstant.REQUEST_SUCCESS;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Order Detail (Collaborator)")
public class CollaboratorOrderDetailController {
    private final OrderDetailService orderDetailService;

    @GetMapping(V3_GET_ORDER_DETAIL + "/{id}")
    public ResponseEntity<?> getOrderDetail(@PathVariable UUID id) {
      return ok(MapperDTO.INSTANCE.toOrderDetailResDto(orderDetailService.findById(id)));
    }

    @PostMapping(V3_CREATE_ORDER_DETAIL)
    public ResponseEntity<?> createOrderDetail(@RequestBody OrderDetailCreatorReqDto dto) throws OrderNotFoundException, ProductCanCreateException, OrderDetailException, ProductNotFoundException {
        UUID id = this.orderDetailService.createOrderDetail(dto);
        return ok(id);
    }

    @PutMapping(V3_UPDATE_ORDER_DETAIL + "/{id}")
    public ResponseEntity<?> updateOrderDetail(@PathVariable UUID id, @RequestBody OrderDetailUpdaterReqDto dto) throws OrderDetailException, ProductNotFoundException {
        UUID uuid = this.orderDetailService.updateOrderDetail(id, dto);
        return ok(id);
    }

    @GetMapping(V3_LIST_ORDER_DETAIL)
    public ResponseEntity<?> getListOrderDetails() {
        return ok(this.orderDetailService.findAll());
    }

    @DeleteMapping(V3_DELETE_ORDER_DETAIL + "/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable("id") UUID id) throws OrderDetailException {
        this.orderDetailService.delete(id);
        return ok(REQUEST_SUCCESS);
    }

}