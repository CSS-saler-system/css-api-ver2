package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.dao.specifications.AccountSpecifications;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Role;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.data.repositories.RoleRepository;
import com.springframework.csscapstone.payload.basic.AccountDto;
import com.springframework.csscapstone.payload.request_dto.admin.AccountCreatorDto;
import com.springframework.csscapstone.payload.response_dto.PageAccountDto;
import com.springframework.csscapstone.payload.response_dto.PageEnterpriseDto;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResponseDto;
import com.springframework.csscapstone.payload.response_dto.collaborator.EnterpriseResponseDto;
import com.springframework.csscapstone.payload.sharing.AccountUpdaterDto;
import com.springframework.csscapstone.services.AccountService;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountExistException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;
import com.springframework.csscapstone.utils.mapper_utils.MapperDTO;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;


    private final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public PageAccountDto getAllDto(
            String name, String phone, String email,
            String address, Integer pageSize, Integer pageNumber) {
        Specification<Account> specifications = Specification.where(Objects.nonNull(name) ? AccountSpecifications.nameContains(name) : null)
                .and(StringUtils.isNotBlank(phone) ? AccountSpecifications.phoneEquals(phone) : null)
                .and(StringUtils.isNotBlank(email) ? AccountSpecifications.emailEquals(email) : null)
                .and(StringUtils.isNotBlank(address) ? AccountSpecifications.addressEquals(address) : null);
        pageSize = Objects.nonNull(pageSize) && (pageSize >= 0) ? pageSize : 10;
        pageNumber = Objects.nonNull(pageNumber) && (pageNumber >= 1) ? pageNumber : 1;

        Page<Account> page = this.accountRepository.findAll(specifications, PageRequest.of(pageNumber - 1, pageSize));
        List<AccountResponseDto> data = page.stream().map(MapperDTO.INSTANCE::toAccountResponseDto).collect(toList());

        return new PageAccountDto(
                data, page.getNumber() + 1, page.getSize(), page.getTotalElements(),
                page.getTotalPages(), page.isFirst(), page.isLast());
    }


    @Override
    public AccountDto getById(UUID id) throws AccountInvalidException, AccountNotFoundException {
        return accountRepository.findById(id)
                .map(MapperDTO.INSTANCE::toAccountDto)
                .orElseThrow(handlerAccountNotFound());
    }


    /**
     * TODO Using By Admin creates Account
     *
     * @param dto
     * @return
     * @throws AccountExistException
     */
    @Transactional
    @Override
    public UUID createAccount(AccountCreatorDto dto) throws AccountExistException {
        Role role = roleRepository.findById(dto.getRole()).orElse(new Role("Collaborator"));
        Account account = new Account()
                .setName(dto.getName())
                .setAddress(dto.getAddress())
                .setDob(dto.getDayOfBirth())
                .setPhone(dto.getPhone())
                .setEmail(dto.getEmail())
                .setPassword(passwordEncoder.encode(dto.getPassword()))
                .setDescription(dto.getDescription())
                .setGender(dto.getGender())
                .setRole(role);
        Account save = accountRepository.save(account);
        return save.getId();
    }

    /**
     * TODO Update Account No Yet Images
     *
     * @param dto
     * @return
     * @throws AccountInvalidException
     */
    @Transactional
    @Override
    public UUID updateAccount(AccountUpdaterDto dto) throws AccountInvalidException {
        Account entity = accountRepository.findById(dto.getId()).orElseThrow(handlerAccountInvalid());

        entity.setName(dto.getName())
                .setEmail(dto.getEmail())
                .setPhone(dto.getPhone())
                .setDob(dto.getDob())
                .setAddress(dto.getAddress())
                .setDescription(dto.getDescription())
                .setGender(dto.getGender());

        this.accountRepository.save(entity);
        return entity.getId();
    }


    @Transactional
    @Override
    public void disableAccount(UUID id) {
        accountRepository.findById(id).ifPresent(x -> {
            x.setIsActive(false);
            accountRepository.save(x);
        });
    }
    //TODO BUG
    @Override
    public PageEnterpriseDto getAllHavingEnterpriseRole(Integer pageNumber, Integer pageSize) {
        pageNumber = Objects.isNull(pageNumber) || pageNumber <= 1 ? 1 : pageNumber;
        pageSize = Objects.isNull(pageSize) || pageSize <= 1 ? 1 : pageSize;

        Page<Account> page = this.accountRepository
                .findAccountByRole("Enterprise", PageRequest.of(pageNumber - 1, pageSize));
        List<EnterpriseResponseDto> data = page
                .getContent().stream()
                .map(MapperDTO.INSTANCE::toEnterpriseResponseDto)
                .collect(toList());
        return new PageEnterpriseDto(
                data, page.getNumber() + 1,
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(), page.isLast());
    }

    private Supplier<AccountInvalidException> handlerAccountInvalid() {
        return () -> new AccountInvalidException(MessagesUtils.getMessage(MessageConstant.Account.INVALID));
    }

    private Supplier<AccountNotFoundException> handlerAccountNotFound() {
        return () -> new AccountNotFoundException(MessagesUtils.getMessage(MessageConstant.Account.NOT_FOUND));
    }
}
