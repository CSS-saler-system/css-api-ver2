package com.springframework.csscapstone.controller.collaborator;

import com.springframework.csscapstone.config.constant.RegexConstant;
import com.springframework.csscapstone.payload.request_dto.customer.collaborator.CustomerCreatorDto;
import com.springframework.csscapstone.payload.request_dto.customer.collaborator.CustomerUpdatorDto;
import com.springframework.csscapstone.services.CustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.UUID;

import static com.springframework.csscapstone.config.constant.ApiEndPoint.Customer.*;
import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequiredArgsConstructor
@Tag(name = "Customer (Collaborator)")
public class CollaboratorCustomerController {
    private final CustomerService customerService;

    @GetMapping(V3_LIST_CUSTOMER)
    public ResponseEntity<?> getListCustomer() {
        return ok(this.customerService.getAllCustomer());
    }

    @GetMapping(V3_GET_CUSTOMER_BY_PHONE)
    public ResponseEntity<?> getCustomerByPhone(@RequestParam("phone") @Pattern(regexp = RegexConstant.REGEX_PHONE) String phone) {
        return ok(this.customerService.findCustomerByPhone(phone));
    }

    @GetMapping(V3_GET_CUSTOMER + "/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") UUID id) {
        return ok(this.customerService.getCustomerById(id));
    }

    @PostMapping(V3_CREATE_CUSTOMER)
    public ResponseEntity<UUID> createNewCustomer(@Valid @RequestBody CustomerCreatorDto dto)
            throws AccountNotFoundException {
        return ok(this.customerService.createCustomer(dto));
    }

    @PutMapping(V3_UPDATE_CUSTOMER)
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody CustomerUpdatorDto dto) throws AccountNotFoundException {
        return ok(this.customerService.updateCustomer(dto));
    }
}
