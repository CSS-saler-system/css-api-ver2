package com.springframework.csscapstone.payload.response_dto.enterprise;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Data
@RequiredArgsConstructor
public class OrderChartEnterpriseResDto implements Serializable {
    private final List<String> months = Arrays.asList(
            "February", "January", "Math",
            "April", "May", "June",
            "July", "August", "September",
            "October", "November", "December");

    private final long[] quantity = new long[12];

}
