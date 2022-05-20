package com.springframework.csscapstone.services;

import com.springframework.csscapstone.payload.request_dto.customer.collaborator.CustomerCreatorDto;
import com.springframework.csscapstone.payload.request_dto.customer.collaborator.CustomerUpdatorDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.CustomerResponseDto;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<CustomerResponseDto> getAllCustomer();

    CustomerResponseDto findCustomerByPhone(String phone);

    //TODO add order to customer
    UUID createCustomer(CustomerCreatorDto dto) throws AccountNotFoundException;

    //TODO update Customer
    UUID updateCustomer(CustomerUpdatorDto dto) throws AccountNotFoundException;

    CustomerResponseDto getCustomerById(UUID id);

}
