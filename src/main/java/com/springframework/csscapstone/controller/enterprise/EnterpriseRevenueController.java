package com.springframework.csscapstone.controller.enterprise;

import com.springframework.csscapstone.payload.response_dto.enterprise.EnterpriseRevenueDto;
import com.springframework.csscapstone.services.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.springframework.csscapstone.config.message.constant.ApiEndPoint.Order.V2_REVENUE_DASHBOARD;

@RestController
@RequiredArgsConstructor
@Tag(name = "Revenue (Enterprise)")
public class EnterpriseRevenueController {
    private final OrderService orderService;

    @GetMapping(V2_REVENUE_DASHBOARD + "/{enterpriseId}")
    public ResponseEntity<?> getRevenueByEnterpriseId(
            @PathVariable("enterpriseId")UUID enterpriseId) {
        Optional<List<EnterpriseRevenueDto>> revenueDtos = this.orderService.getRevenue(enterpriseId);
        return ResponseEntity.ok(revenueDtos);
    }

}
