package com.springframework.csscapstone.css_business.model_dto.basic;

import com.springframework.csscapstone.css_data.status.RequestStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RequestSellingProductDto implements Serializable {
    private final UUID id;
    private final LocalDateTime dateTimeRequest;
    private final RequestStatus requestStatus;
    private final long quantityProduct;
}
