package com.springframework.csscapstone.data.dao.specifications;

import com.springframework.csscapstone.data.domain.Campaign;
import com.springframework.csscapstone.data.domain.Campaign_;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

import static com.springframework.csscapstone.data.dao.specifications.ContainsString.contains;

public class CampaignSpecifications {
    public static Specification<Campaign> containsName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.get(Campaign_.NAME), contains(name));
    }

    public static Specification<Campaign> smallerKpi(Long smallerKpi) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get(Campaign_.KPI_SALE_PRODUCT), smallerKpi);
    }

    public static Specification<Campaign> greaterKpi(Long greaterKpi) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(
                root.get(Campaign_.KPI_SALE_PRODUCT), greaterKpi);
    }

    public static Specification<Campaign> afterStartDate(LocalDateTime startDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .lessThan(root.get(Campaign_.START_DATE), startDate);
    }

    public static Specification<Campaign> beforeEndDate(LocalDateTime endDate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .greaterThan(root.get(Campaign_.END_DATE), endDate);
    }

}
