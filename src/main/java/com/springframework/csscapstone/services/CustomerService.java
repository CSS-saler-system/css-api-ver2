package com.springframework.csscapstone.services;

import com.springframework.csscapstone.payload.request_dto.collaborator.CustomerCreatorReqDto;
import com.springframework.csscapstone.payload.request_dto.collaborator.CustomerUpdaterReqDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.CustomerResDto;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;

public interface CustomerService {
    List<CustomerResDto> getAllCustomer();

    CustomerResDto findCustomerByPhone(String phone);

    //TODO add order to customer <Completed></>
    UUID createCustomer(CustomerCreatorReqDto dto) throws AccountNotFoundException;

    //TODO update Customer <Completed></>
    UUID updateCustomer(CustomerUpdaterReqDto dto) throws AccountNotFoundException;

    CustomerResDto getCustomerById(UUID id);

}
