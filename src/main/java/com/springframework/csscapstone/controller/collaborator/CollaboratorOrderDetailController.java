package com.springframework.csscapstone.controller.collaborator;

import com.springframework.csscapstone.services.OrderDetailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Order Detail (Collaborator)")
public class CollaboratorOrderDetailController {
    private final OrderDetailService orderDetailService;

//    @GetMapping(V3_GET_ORDER_DETAIL + "/{orderDetailId}")
//    public ResponseEntity<?> getOrderDetail(@PathVariable("orderDetailId") UUID id) {
//        return ok(MapperDTO.INSTANCE.toOrderDetailResDto(orderDetailService.findById(id)));
//    }

//    @PostMapping(V3_CREATE_ORDER_DETAIL)
//    public ResponseEntity<?> createOrderDetail(@RequestBody OrderDetailCreatorReqDto dto) throws OrderNotFoundException, ProductCanCreateException, OrderDetailException, ProductNotFoundException {
//        UUID id = this.orderDetailService.createOrderDetail(dto);
//        return ok(id);
//    }

//    @PutMapping(V3_UPDATE_ORDER_DETAIL + "/{orderDetailId}")
//    public ResponseEntity<?> updateOrderDetail(
//            @PathVariable("orderDetailId") UUID id,
//            @RequestBody OrderDetailUpdaterReqDto dto) throws OrderDetailException, ProductNotFoundException {
//        UUID uuid = this.orderDetailService.updateOrderDetail(id, dto);
//        return ok(id);
//    }

//    @GetMapping(V3_LIST_ORDER_DETAIL)
//    public ResponseEntity<?> getListOrderDetails() {
//        return ok(this.orderDetailService.findAll());
//    }

//    @DeleteMapping(V3_DELETE_ORDER_DETAIL + "/{orderDetailId}")
//    public ResponseEntity<?> deleteOrderDetail(@PathVariable("orderDetailId") UUID id) throws OrderDetailException {
//        this.orderDetailService.delete(id);
//        return ok(REQUEST_SUCCESS);
//    }

}
