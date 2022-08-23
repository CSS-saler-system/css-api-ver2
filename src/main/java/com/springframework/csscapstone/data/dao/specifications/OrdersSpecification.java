package com.springframework.csscapstone.data.dao.specifications;

import com.springframework.csscapstone.data.domain.*;
import com.springframework.csscapstone.data.status.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class OrdersSpecification {
    public static Specification<Order> equalsStatus(OrderStatus status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Order_.STATUS), status);
    }

    public static Specification<Order> equalsCollaborator(Account collaborator) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Order_.ACCOUNT), collaborator);
    }

    public static Specification<Order> excludeDisableStatus() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get(Order_.STATUS), OrderStatus.DISABLED);
    }

    public static Specification<Order> equalsEnterpriseId(UUID enterpriseId) {
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            return criteriaBuilder.equal(
                    root
                            .join(Order_.orderDetails)
                            .join(OrderDetail_.product)
                            .join(Product_.account)
                            .get(Account_.id), enterpriseId);
        };
    }
}
