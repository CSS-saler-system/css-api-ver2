package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Customer;
import com.springframework.csscapstone.data.domain.CustomerResponseDto;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.data.repositories.CustomerRepository;
import com.springframework.csscapstone.payload.request_dto.customer.CustomerCreatorDto;
import com.springframework.csscapstone.payload.request_dto.customer.CustomerUpdatorDto;
import com.springframework.csscapstone.services.CustomerService;
import com.springframework.csscapstone.utils.exception_utils.EntityNotFoundException;
import com.springframework.csscapstone.utils.exception_utils.customer_exception.CustomerExistedException;
import com.springframework.csscapstone.utils.exception_utils.customer_exception.CustomerNotFoundException;
import com.springframework.csscapstone.utils.mapper_utils.MapperDTO;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<CustomerResponseDto> getAllCustomer() {
        return this.customerRepository.findAll()
                .stream()
                .map(MapperDTO.INSTANCE::toCustomerResponseDto)
                .collect(toList());
    }

    @Override
    public CustomerResponseDto findCustomerByPhone(String phone) throws CustomerNotFoundException {
        return this.customerRepository
                .getCustomerByPhone(phone)
                .map(MapperDTO.INSTANCE::toCustomerResponseDto)
                .orElseThrow(getCustomerNotFoundExceptionSupplier());
    }

    //TODO create customer
    @Override
    public UUID createCustomer(CustomerCreatorDto dto) throws AccountNotFoundException {

        if (this.customerRepository.findByPhone(dto.getPhone()).isPresent()) {
            throw new CustomerExistedException(MessagesUtils.getMessage(MessageConstant.Customer.EXISTED));
        }

        Account accountCreator = this.accountRepository
                .findById(dto.getAccountCreator().getId())
                .orElseThrow(getAccountNotFoundExceptionSupplier());

        Customer customer = new Customer()
                .setAddress(dto.getAddress())
                .setDescription(dto.getDescription())
                .setDob(dto.getDob())
                .setPhone(dto.getPhone())
                .setName(dto.getName())
                .setAccountCreator(accountCreator);
        return this.customerRepository.save(customer).getId();
    }

    //TODO update Customer
    @Override
    public UUID updateCustomer(CustomerUpdatorDto dto) {
        Account accountUpdator = this.accountRepository.findById(dto.getAccountUpdater().getId())
                .orElseThrow(getAccountNotFoundExceptionSupplier());

        Customer customer = this.customerRepository
                .findById(dto.getId())
                .orElseThrow(getCustomerNotFoundExceptionSupplier());

        customer
                .setAddress(dto.getAddress())
                .setDescription(dto.getDescription())
                .setDob(dto.getDob())
                .setPhone(dto.getPhone())
                .setName(dto.getName())
                .setAccountUpdater(accountUpdator);
        return this.customerRepository.save(customer).getId();
    }

    @Override
    public CustomerResponseDto getCustomerById(UUID id) throws CustomerNotFoundException {
        return this.customerRepository.findById(id)
                .map(MapperDTO.INSTANCE::toCustomerResponseDto)
                .orElseThrow(this.getCustomerNotFoundExceptionSupplier());
    }

    private Supplier<EntityNotFoundException> getAccountNotFoundExceptionSupplier() {
        return () -> new EntityNotFoundException(MessagesUtils.getMessage(MessageConstant.Account.NOT_FOUND));
    }

    private Supplier<CustomerNotFoundException> getCustomerNotFoundExceptionSupplier() {
        return () -> new CustomerNotFoundException(MessagesUtils.getMessage(MessageConstant.Customer.NOT_FOUND));
    }

}
