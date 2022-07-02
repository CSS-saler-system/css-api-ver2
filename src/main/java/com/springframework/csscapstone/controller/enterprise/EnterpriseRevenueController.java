package com.springframework.csscapstone.controller.enterprise;

import com.springframework.csscapstone.services.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Order.V2_REVENUE_DASHBOARD;

@RestController
@RequiredArgsConstructor
@Tag(name = "Revenue (Enterprise)")
public class EnterpriseRevenueController {
    private final OrderService orderService;

    @GetMapping(V2_REVENUE_DASHBOARD + "/{enterprise}")
    public ResponseEntity<?> getRevenueByEnterpriseId(
            @PathVariable("enterpriseId")UUID enterpriseId) {
        this.orderService.getRevenue(enterpriseId);
        return ResponseEntity.ok("");
    }

}
