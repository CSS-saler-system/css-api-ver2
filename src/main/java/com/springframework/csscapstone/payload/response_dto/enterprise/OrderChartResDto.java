package com.springframework.csscapstone.payload.response_dto.enterprise;

import lombok.Data;

@Data
public class OrderChartResDto {

    private final Integer totalOfOrder;
    private final Integer numberOfSuccessOrder;
    private final Integer month;


}
