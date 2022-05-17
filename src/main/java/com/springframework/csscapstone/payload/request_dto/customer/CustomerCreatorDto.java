package com.springframework.csscapstone.payload.request_dto.customer;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CustomerCreatorDto implements Serializable {
    @NotNull
    private final String name;
    @NotNull
    private final String phone;
    @NotNull
    private final String address;
    @NotNull
    private final LocalDate dob;
    @NotNull
    private final AccountDto accountCreator;
    @NotNull
    private final String description;

    @Data
    public static class AccountDto implements Serializable {
        private final UUID id;
    }
}
