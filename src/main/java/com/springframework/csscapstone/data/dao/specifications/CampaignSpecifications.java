package com.springframework.csscapstone.data.dao.specifications;

import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Campaign;
import com.springframework.csscapstone.data.domain.Campaign_;
import com.springframework.csscapstone.data.status.CampaignStatus;
import org.assertj.core.util.Arrays;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.springframework.csscapstone.data.dao.specifications.ContainsString.contains;

public class CampaignSpecifications {

    public static Specification<Campaign> excludeStatus(CampaignStatus status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get(Campaign_.CAMPAIGN_STATUS), status);
    }

    public static Specification<Campaign> equalsStatus(CampaignStatus status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Campaign_.CAMPAIGN_STATUS), status);
    }
//
//    public static Specification<Campaign> excludeStatus(CampaignStatus status, CampaignStatus... exclude) {
//        return
//                (root, query, criteriaBuilder) -> {
//                    Predicate conjunction = criteriaBuilder.not(root
//                            .get(Campaign_.CAMPAIGN_STATUS).in(Arrays.asList(exclude)));
//
//                    criteriaBuilder.and(criteriaBuilder
//                            .equal(root.get(Campaign_.CAMPAIGN_STATUS), status), conjunction);
//
//                    return criteriaBuilder.and(conjunction);
//                };
//    }
    public static Specification<Campaign> excludeStatus(CampaignStatus... exclude) {
        return
                (root, query, criteriaBuilder) -> criteriaBuilder
                        .not(root.get(Campaign_.CAMPAIGN_STATUS).in(Arrays.asList(exclude)));
    }

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

    public static Specification<Campaign> equalsEnterpriseId(Account enterprise) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Campaign_.ACCOUNT), enterprise);
    }
}
