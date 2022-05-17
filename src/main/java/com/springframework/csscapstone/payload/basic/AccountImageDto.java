package com.springframework.csscapstone.payload.basic;

import com.springframework.csscapstone.data.status.AccountImageType;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class AccountImageDto implements Serializable {
    private final UUID id;
    private final AccountImageType type;
    private final String path;
}
