package com.springframework.csscapstone.payload.response_dto.enterprise;

import lombok.Data;

@Data
public class RevenueChartEnterpriseResDto {
    private final long[] month = new long[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
    private final double[] revenue = new double[12];
}
