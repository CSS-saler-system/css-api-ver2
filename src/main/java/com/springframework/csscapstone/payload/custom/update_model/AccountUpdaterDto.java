package com.springframework.csscapstone.payload.custom.update_model;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class AccountUpdaterDto {
    //validation must have id
    private final UUID id;
    private final String name;
    private final LocalDate dob;
    private final String phone;
    private final String email;
    private final String address;
    private final String description;
    private final boolean gender;
}
