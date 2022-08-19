package com.springframework.csscapstone.data.dao.specifications;

import com.springframework.csscapstone.data.domain.Account_;
import com.springframework.csscapstone.data.domain.FeedBack;
import com.springframework.csscapstone.data.domain.FeedBack_;
import com.springframework.csscapstone.data.status.FeedbackStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class FeedBackSpecifications {

    public static Specification<FeedBack> equalsByEnterpriseId(UUID enterpriseId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get(FeedBack_.CREATOR).get(Account_.ID), enterpriseId);
    }

    public static Specification<FeedBack> equalsStatus(FeedbackStatus status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get(FeedBack_.FEEDBACK_STATUS), status);
    }

}
