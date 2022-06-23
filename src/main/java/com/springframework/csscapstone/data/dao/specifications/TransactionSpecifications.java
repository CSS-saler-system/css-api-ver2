package com.springframework.csscapstone.data.dao.specifications;

import com.springframework.csscapstone.config.constant.ApiEndPoint;
import com.springframework.csscapstone.data.domain.Transactions;
import com.springframework.csscapstone.data.domain.Transactions_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionSpecifications {

    public static Specification<Transactions> equalId(UUID uuid) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Transactions_.ID), uuid);
    }

    public static Specification<Transactions> beforeDate(LocalDateTime beforeDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get(Transactions_.CREATE_TRANSACTION_DATE), beforeDate);
    }

    public static Specification<Transactions> afterDate(LocalDateTime afterDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get(Transactions_.LAST_MODIFIED_DATE), afterDate);
    }
}
