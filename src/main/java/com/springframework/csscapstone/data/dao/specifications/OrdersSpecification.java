package com.springframework.csscapstone.data.dao.specifications;

import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Order;
import com.springframework.csscapstone.data.domain.Order_;
import com.springframework.csscapstone.data.status.OrderStatus;
import org.springframework.data.jpa.domain.Specification;

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
//                        criteriaBuilder.notEqual(root.get(Order_.STATUS), OrderStatus.CANCELED);
    }

}
