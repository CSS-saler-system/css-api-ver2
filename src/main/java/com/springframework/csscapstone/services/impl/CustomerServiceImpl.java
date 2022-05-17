package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Customer;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.data.repositories.CustomerRepository;
import com.springframework.csscapstone.payload.request_dto.customer.CustomerCreatorDto;
import com.springframework.csscapstone.payload.request_dto.customer.CustomerUpdatorDto;
import com.springframework.csscapstone.services.CustomerService;
import com.springframework.csscapstone.utils.exception_utils.customer.CustomerNotFoundException;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    @Override
    public List<Customer> getAllCustomer() {
        return this.customerRepository.findAll();
    }

    @Override
    public Customer findCustomerByPhone(String phone) throws CustomerNotFoundException {
        return this.customerRepository
                .getCustomerByPhone(phone)
                .orElseThrow(getCustomerNotFoundExceptionSupplier());
    }

    //TODO add order to customer
    @Override
    public UUID createCustomer(CustomerCreatorDto dto) throws AccountNotFoundException {
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
    public Customer updateCustomer(CustomerUpdatorDto dto) throws AccountNotFoundException {
        Account accountUpdator = this.accountRepository.findById(dto.getAccountUpdater().getId())
                .orElseThrow(getAccountNotFoundExceptionSupplier());
        Customer customer = new Customer()
                .setAddress(dto.getAddress())
                .setDescription(dto.getDescription())
                .setDob(dto.getDob())
                .setPhone(dto.getPhone())
                .setName(dto.getName())
                .setAccountCreator(accountUpdator);
        return this.customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerById(UUID id) throws CustomerNotFoundException {
        return this.customerRepository.findById(id)
                .orElseThrow(this.getCustomerNotFoundExceptionSupplier());
    }

    private Supplier<AccountNotFoundException> getAccountNotFoundExceptionSupplier() {
        return () -> new AccountNotFoundException(MessagesUtils.getMessage(MessageConstant.Account.NOT_FOUND));
    }

    private Supplier<CustomerNotFoundException> getCustomerNotFoundExceptionSupplier() {
        return () -> new CustomerNotFoundException(MessagesUtils.getMessage(MessageConstant.Customer.NOT_FOUND));
    }

}
