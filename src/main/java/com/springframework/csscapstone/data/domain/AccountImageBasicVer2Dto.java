package com.springframework.csscapstone.data.domain;

import com.springframework.csscapstone.data.status.AccountImageType;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class AccountImageBasicVer2Dto implements Serializable {
    private final UUID id;
    private final AccountImageType type;
    private final String path;
}
