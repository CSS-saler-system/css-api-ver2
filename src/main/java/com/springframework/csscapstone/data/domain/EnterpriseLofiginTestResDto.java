package com.springframework.csscapstone.data.domain;

import com.springframework.csscapstone.data.status.AccountImageType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class EnterpriseLofiginTestResDto implements Serializable {
    private final UUID id;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String description;
    private final Double point;
    private final AccountImageDto avatar;
    private final AccountImageDto licenses;

    @Data
    public static class AccountImageDto implements Serializable {
        private final UUID id;
        private final AccountImageType type;
        private final String path;
    }
}
