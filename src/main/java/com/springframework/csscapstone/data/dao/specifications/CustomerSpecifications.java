package com.springframework.csscapstone.data.dao.specifications;

import com.springframework.csscapstone.data.domain.Customer;
import com.springframework.csscapstone.data.domain.Customer_;
import org.springframework.data.jpa.domain.Specification;

import static com.springframework.csscapstone.data.dao.specifications.ContainsString.contains;

public class CustomerSpecifications {
    public Specification<Customer> containsName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.get(Customer_.NAME), contains(name));
    }

    public Specification<Customer> equalsPhome(String phone) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .equal(root.get(Customer_.PHONE), contains(phone));
    }

    public Specification<Customer> likeAddress(String address) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.get(Customer_.ADDRESS), contains(address));
    }
}
