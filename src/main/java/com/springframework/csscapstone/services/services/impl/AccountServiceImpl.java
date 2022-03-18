package com.springframework.csscapstone.services.services.impl;

import com.springframework.csscapstone.config.constant.MessageConstant;
import com.springframework.csscapstone.services.model_dto.basic.AccountDto;
import com.springframework.csscapstone.services.model_dto.custom.creator_model.AccountRegisterDto;
import com.springframework.csscapstone.services.model_dto.custom.update_model.AccountUpdaterDto;
import com.springframework.csscapstone.services.services.AccountService;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Account_;
import com.springframework.csscapstone.data.domain.Role;
import com.springframework.csscapstone.data.repositories.AccountRepository;
import com.springframework.csscapstone.data.repositories.RoleRepository;
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

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final PasswordEncoder passwordEncoder;
    private final EntityManager em;
    private final AccountRepository repositories;
    private final RoleRepository roleRepository;
    //    private final AccountDao AccountDao;
    private final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    /**
     * Check does account have duplication information
     */
    BiPredicate<AccountRepository, AccountRegisterDto> _checkDuplicationPredicate = (repository, dto) -> {

        //check duplicate username
        repository.findAccountByUsername(dto.getUsername()).ifPresent(x -> {
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
    //todo DAO:
    @Override
    public List<AccountDto> getAllDto(String name, String phone,
                                      String email, String address, String description, boolean status) {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = builder.createQuery(Account.class);
        Root<Account> root = query.from(Account.class);

        CriteriaQuery<Account> processQuery = query
                .where(builder.and(
                        builder.like(root.get(Account_.NAME), name),
                        builder.like(root.get(Account_.ADDRESS), address),
                        builder.like(root.get(Account_.DESCRIPTION), description),
                        builder.like(root.get(Account_.EMAIL), email),
                        builder.like(root.get(Account_.PHONE), phone),
                        builder.equal(root.get(Account_.IS_ACTIVE), status)));

        return em.createQuery(processQuery)
                .getResultList().stream().map(MapperDTO.INSTANCE::toAccountDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto getById(UUID id) throws AccountInvalidException {
        return repositories.findById(id)
                .map(MapperDTO.INSTANCE::toAccountDto)
                .orElseThrow(() -> new AccountInvalidException(MessagesUtils.getMessage(MessageConstant.Account.NOT_FOUND)));
    }

    @Transactional
    @Override
    public UUID registerAccount(AccountRegisterDto dto) throws AccountExistException {
        boolean test = _checkDuplicationPredicate.test(repositories, dto);
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
                .setUsername(dto.getUsername())
                .setPassword(passwordEncoder.encode(dto.getPassword()))
                .setAddress(dto.getAddress())
                .setDescription(dto.getDescription())
                .setGender(dto.getGender())
                .addRole(role);

        this.repositories.save(account);
        this.roleRepository.save(account.getRole());
        return account.getId();
    }

    @Override
    public UUID updateAccount(AccountUpdaterDto dto) throws AccountInvalidException {
        Account entity = repositories.findById(dto.getId())
                .filter(x -> x.getId().equals(dto.getId()))
                .filter(x -> x.getEmail().equals(dto.getEmail()))
                .filter(x -> x.getPhone().equals(dto.getPhone()))
                .orElseThrow(() -> new AccountInvalidException(MessagesUtils.getMessage(MessageConstant.Account.INVALID)));

        entity.setName(dto.getName())
                .setDob(dto.getDob())
                .setAddress(dto.getAddress())
                .setDescription(dto.getDescription())
                .setGender(dto.isGender());
        this.repositories.save(entity);
        return entity.getId();
    }

    @Transactional
    @Override
    public void disableAccount(UUID id) {
        repositories.findById(id).ifPresent(x -> {
            x.setIsActive(false);
            repositories.save(x);
        });
    }

}
