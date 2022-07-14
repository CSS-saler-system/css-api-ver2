package com.springframework.csscapstone.payload.response_dto.enterprise;

import lombok.Data;

@Data
public class EnterpriseRevenueDto {
    private final Integer month;
    private final Double maxRevenue;

}
