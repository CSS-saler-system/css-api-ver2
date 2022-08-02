package com.springframework.csscapstone.data.dao.specifications;

import com.springframework.csscapstone.data.domain.Account_;
import com.springframework.csscapstone.data.domain.Product_;
import com.springframework.csscapstone.data.domain.RequestSellingProduct;
import com.springframework.csscapstone.data.domain.RequestSellingProduct_;
import com.springframework.csscapstone.data.status.RequestStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class RequestSellingProductSpecifications {
    public static Specification<RequestSellingProduct> containsEnterpriseId(UUID enterpriseId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(RequestSellingProduct_.product)
                .get(Product_.ACCOUNT)
                .get(Account_.ID), enterpriseId);
    }

    public static Specification<RequestSellingProduct> belongToCollaborator(UUID collaboratorId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get(RequestSellingProduct_.ACCOUNT).get(Account_.ID), collaboratorId);
    }

    public static Specification<RequestSellingProduct> equalsStatus(RequestStatus status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(RequestSellingProduct_.REQUEST_STATUS), status);
    }
}
