package com.springframework.csscapstone.data.dao.specifications;

import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Account_;
import com.springframework.csscapstone.data.domain.Role_;
import org.springframework.data.jpa.domain.Specification;

import static com.springframework.csscapstone.data.dao.specifications.ContainsString.contains;

public class AccountSpecifications {

    public static Specification<Account> nameContains(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Account_.NAME), contains(name));
    }
    public static Specification<Account> phoneEquals(String phone) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Account_.PHONE), phone);
    }
    public static Specification<Account> addressEquals(String address) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Account_.ADDRESS), contains(address));
    }

    public static Specification<Account> genderEquals(boolean gender) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Account_.gender), gender);
    }


    public static Specification<Account> emailEquals(String email) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Account_.EMAIL), email);
    }

    public static Specification<Account> getAllRoleEnterprise() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Account_.ROLE).get(Role_.NAME), "Enterprise");
    }

    public static Specification<Account> getEnterpriseByStatus(boolean isActive) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Account_.IS_ACTIVE), isActive);
    }

}
