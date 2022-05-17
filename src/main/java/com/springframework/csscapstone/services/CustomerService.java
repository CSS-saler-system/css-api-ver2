package com.springframework.csscapstone.services;

import com.springframework.csscapstone.data.domain.Customer;
import com.springframework.csscapstone.payload.request_dto.customer.CustomerCreatorDto;
import com.springframework.csscapstone.payload.request_dto.customer.CustomerUpdatorDto;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<Customer> getAllCustomer();

    Customer findCustomerByPhone(String phone);

    //TODO add order to customer
    UUID createCustomer(CustomerCreatorDto dto) throws AccountNotFoundException;

    //TODO update Customer
    Customer updateCustomer(CustomerUpdatorDto dto) throws AccountNotFoundException;

    Customer getCustomerById(UUID id);

}
