package com.springframework.csscapstone.payload.request_dto.moderator;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Data
public class TransactionHandler implements Serializable {
    @NotNull(message = "The transactionId must be not null")
    private final UUID transactionId;
    @NotNull(message = "The accountApproverId must be not null")
    private final UUID accountApproverId;

    public TransactionHandler(
            @JsonProperty("transactionId") UUID transactionId,
            @JsonProperty("accountApproverId") UUID accountApproverId) {
        this.transactionId = transactionId;
        this.accountApproverId = accountApproverId;
    }
}
