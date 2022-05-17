package com.springframework.csscapstone.payload.basic;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class AccountDto implements Serializable {
    private final UUID id;
    private final String name;
    private final LocalDate dob;
    private final String phone;
    private final String email;
    private final String address;
    private final String description;
    private final boolean gender;
    private final double point;
    private final List<String> tokens;
}
