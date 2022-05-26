package com.springframework.csscapstone.data.dao.specifications;

import com.springframework.csscapstone.data.domain.Role;
import com.springframework.csscapstone.data.domain.Role_;
import org.springframework.data.jpa.domain.Specification;

public final class RoleSpecification {

    public static Specification<Role> equalNames(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Role_.NAME), name);

    }
}
