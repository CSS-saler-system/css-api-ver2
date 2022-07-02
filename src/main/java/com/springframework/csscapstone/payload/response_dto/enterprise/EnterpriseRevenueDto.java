package com.springframework.csscapstone.payload.response_dto.enterprise;

import lombok.Data;

@Data
public class EnterpriseRevenueDto {
    private final Long unitMonth;
    private final Double minRevenue;
    private final Double maxRevenue;

}
