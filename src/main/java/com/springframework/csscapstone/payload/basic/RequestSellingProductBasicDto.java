package com.springframework.csscapstone.payload.basic;

import com.springframework.csscapstone.data.status.RequestStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RequestSellingProductBasicDto implements Serializable {
    private final UUID id;
    private final LocalDateTime dateTimeRequest;
    private final RequestStatus requestStatus;
    private final long quantityProduct;
}