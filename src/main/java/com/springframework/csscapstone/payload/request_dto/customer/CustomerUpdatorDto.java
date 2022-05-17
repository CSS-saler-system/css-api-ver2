package com.springframework.csscapstone.payload.request_dto.customer;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CustomerUpdatorDto implements Serializable {
    private final String name;
    private final String phone;
    private final String address;
    private final LocalDate dob;
    private final AccountDto accountUpdater;
    private final String description;

    @Data
    public static class AccountDto implements Serializable {
        private final UUID id;
    }
}
