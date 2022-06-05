package com.springframework.csscapstone.payload.basic;

import com.springframework.csscapstone.data.status.TransactionStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionsBasicDto implements Serializable {
    private final UUID id;
    private final LocalDateTime createTransaction;
    private final double point;
    private final TransactionStatus transactionStatus;
}
