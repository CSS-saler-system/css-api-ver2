package com.springframework.csscapstone.data.dao.impl;

import com.springframework.csscapstone.data.dao.AccountDAO;
import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Account_;
import com.springframework.csscapstone.services.model_dto.basic.AccountDto;
import com.springframework.csscapstone.utils.mapper_utils.MapperDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountDAOImpl implements AccountDAO {
    private final EntityManager em;

    @Override
    public List<AccountDto> findDtoCriteria(String name, String phone, String email, String address, String description, boolean status) {
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
}
