package com.springframework.csscapstone.css_business.model_dto.basic;

import com.springframework.csscapstone.css_data.status.TransactionStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionsDto implements Serializable {
    private final UUID id;
    private final LocalDateTime createTransaction;
    private final double point;
    private final TransactionStatus transactionStatus;
}
