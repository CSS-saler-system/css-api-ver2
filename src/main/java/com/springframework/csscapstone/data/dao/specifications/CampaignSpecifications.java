package com.springframework.csscapstone.data.dao.specifications;

import com.springframework.csscapstone.data.domain.Campaign;
import com.springframework.csscapstone.data.domain.Campaign_;
import org.springframework.data.jpa.domain.Specification;

import static com.springframework.csscapstone.data.dao.specifications.ContainsString.contains;

public class CampaignSpecifications {
    public static Specification<Campaign> containsName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.get(Campaign_.NAME), contains(name));
    }
}
