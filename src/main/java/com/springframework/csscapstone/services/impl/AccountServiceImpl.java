package com.springframework.csscapstone.services.impl;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.data.dao.AccountDAO;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Role;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.data.repositories.RoleRepository;
import com.springframework.csscapstone.payload.response_dto.collaborator.EnterpriseResponseDto;
import com.springframework.csscapstone.services.AccountService;
import com.springframework.csscapstone.payload.basic.AccountDto;
import com.springframework.csscapstone.payload.request_dto.admin.AccountRegisterDto;
import com.springframework.csscapstone.payload.request_dto.admin.AccountUpdaterDto;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountExistException;
import com.springframework.csscapstone.utils.exception_utils.account_exception.AccountInvalidException;
import com.springframework.csscapstone.utils.mapper_utils.MapperDTO;
import com.springframework.csscapstone.utils.message_utils.MessagesUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiPredicate;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AccountDAO accountDAO;
    private final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    /**
     * Check does account have duplication information
     */
    BiPredicate<AccountRepository, AccountRegisterDto> _checkDuplicationPredicate = (repository, dto) -> {

        //check duplicate username
        repository.findAccountByEmail(dto.getEmail()).ifPresent(x -> {
            throw new IllegalArgumentException(MessagesUtils.getMessage(MessageConstant.Account.USER_NAME_EXISTED));
        });

        //check duplicate email
        repository.findAccountByEmail(dto.getEmail()).ifPresent(x -> {
            throw new IllegalArgumentException(MessagesUtils.getMessage(MessageConstant.Account.EMAIL_EXITED));
        });

        //check duplicate password
        repository.findAccountsByPhone(dto.getPhone()).ifPresent(x -> {
            throw new IllegalArgumentException(MessagesUtils.getMessage(MessageConstant.Account.PHONE_EXISTED));
        });
        return true;
    };
    //todo DAO: <Completed></>
    @Override
    public List<AccountDto> getAllDto(String name, String phone,
                                      String email, String address, String description, boolean status) {
        return accountDAO.findDtoCriteria(name, phone, email, address, description, status);
    }

    @Override
    public AccountDto getById(UUID id) throws AccountInvalidException {
        return accountRepository.findById(id)
                .map(MapperDTO.INSTANCE::toAccountDto)
                .orElseThrow(() -> new AccountInvalidException(MessagesUtils.getMessage(MessageConstant.Account.NOT_FOUND)));
    }

    @Transactional
    @Override
    public UUID registerAccount(AccountRegisterDto dto) throws AccountExistException {
        boolean test = _checkDuplicationPredicate.test(accountRepository, dto);
        //true ..., else throws exception
        if (test) {
            Role role = roleRepository.getById(dto.getRole().getId());
            return Optional.of(new Account())
                    .map(x -> createAccount(dto, role, x))
                    .orElseThrow(() -> new AccountExistException(MessagesUtils.getMessage(MessageConstant.Account.EXISTED)));

        }
        return null;
    }

    private UUID createAccount(AccountRegisterDto dto, Role role, Account account) {
        account.setName(dto.getName())
                .setDob(dto.getDayOfBirth())
                .setPhone(dto.getPhone())
                .setEmail(dto.getEmail())
                .setPassword(passwordEncoder.encode(dto.getPassword()))
                .setAddress(dto.getAddress())
                .setDescription(dto.getDescription())
                .setGender(dto.getGender())
                .addRole(role);

        this.accountRepository.save(account);
        this.roleRepository.save(account.getRole());
        return account.getId();
    }

    @Transactional
    @Override
    public UUID updateAccount(AccountUpdaterDto dto) throws AccountInvalidException {
        Account entity = accountRepository.findById(dto.getId())
                .filter(x -> x.getId().equals(dto.getId()))
                .filter(x -> x.getEmail().equals(dto.getEmail()))
                .filter(x -> x.getPhone().equals(dto.getPhone()))
                .orElseThrow(() -> new AccountInvalidException(MessagesUtils.getMessage(MessageConstant.Account.INVALID)));

        entity.setName(dto.getName())
                .setDob(dto.getDob())
                .setAddress(dto.getAddress())
                .setDescription(dto.getDescription())
                .setGender(dto.isGender());
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
    public List<EnterpriseResponseDto> getAllHavingEnterpriseRole() {
        return this.accountRepository
                .findAccountByRole("Enterprise")
                .stream().map(MapperDTO.INSTANCE::toEnterpriseResponseDto)
                .collect(toList());
    }

}
