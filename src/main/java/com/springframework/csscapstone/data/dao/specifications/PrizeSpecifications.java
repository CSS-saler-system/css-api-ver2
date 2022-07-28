package com.springframework.csscapstone.data.dao.specifications;

import com.springframework.csscapstone.data.domain.Account;
import com.springframework.csscapstone.data.domain.Account_;
import com.springframework.csscapstone.data.domain.Prize;
import com.springframework.csscapstone.data.domain.Prize_;
import com.springframework.csscapstone.data.status.CampaignStatus;
import com.springframework.csscapstone.data.status.PrizeStatus;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

import static com.springframework.csscapstone.data.dao.specifications.ContainsString.contains;

public class PrizeSpecifications {
    public static Specification<Prize> containsName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder
                .like(root.get(Prize_.NAME), contains(name));
    }

    public static Specification<Prize> belongEnterpriseId(Account enterprise) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Prize_.CREATOR), enterprise);
    }
    public static Specification<Prize> excludeDisableStatus() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get(Prize_.PRIZE_STATUS), PrizeStatus.DISABLE);
    }

}
