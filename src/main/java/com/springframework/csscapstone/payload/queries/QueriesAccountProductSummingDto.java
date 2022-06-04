package com.springframework.csscapstone.payload.queries;

import com.springframework.csscapstone.data.domain.Account;
import lombok.Data;

@Data
public class QueriesAccountProductSummingDto {
    private final Account account;
    private final Long sumProduct;



}
