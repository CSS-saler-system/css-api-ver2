package com.springframework.csscapstone.data.dao.specifications;

import com.springframework.csscapstone.data.domain.Prize;
import com.springframework.csscapstone.data.domain.Prize_;
import org.springframework.data.jpa.domain.Specification;

import static com.springframework.csscapstone.data.dao.specifications.ContainsString.contains;

public class PrizeSpecifications {
    public static Specification<Prize> containsName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.get(Prize_.NAME), contains(name));
    }
}
