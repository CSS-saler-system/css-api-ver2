package com.springframework.csscapstone.controller.collaborator;

import com.springframework.csscapstone.data.domain.Customer;
import com.springframework.csscapstone.payload.request_dto.customer.CustomerCreatorDto;
import com.springframework.csscapstone.payload.request_dto.customer.CustomerUpdatorDto;
import com.springframework.csscapstone.services.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDate;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Customer.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@Tag(name = "Customer (Collaborator)")
public class CollaboratorCustomerController {
    private final CustomerService customerService;

    @GetMapping(V3_LIST_CUSTOMER)
    public ResponseEntity<?> getListCustomer(
            @RequestParam(value = "name", required = false) String customerName,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "day of birth", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate dayOfBirth,
            @RequestParam(value = "description", required = false) String description) {
        return ok(this.customerService.getAllCustomer());
    }

    @GetMapping(V3_GET_CUSTOMER + "/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") UUID id) {
        Customer customerById = this.customerService.getCustomerById(id);
        return ok(customerById);
    }

    @PostMapping(V3_CREATE_CUSTOMER)
    public ResponseEntity<UUID> createNewCustomer(@RequestBody CustomerCreatorDto dto) throws AccountNotFoundException {
        return ok(this.customerService.createCustomer(dto));
    }

    @PutMapping(V3_UPDATE_CUSTOMER)
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerUpdatorDto dto) throws AccountNotFoundException {
        return ok(this.customerService.updateCustomer(dto));
    }


}
