package com.springframework.csscapstone.controller.enterprise;

import com.springframework.csscapstone.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EnterpriseOrderController {
    private final OrderService orderService;

}
