package com.springframework.csscapstone.payload.custom.creator_model;

import com.springframework.csscapstone.payload.basic.RoleDto;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class AccountRegisterDto {

    private final String name;
    private final LocalDate dayOfBirth;

    @NotNull(message = "phone is mandatory")
    private final String phone;

    @Email(message = "Email is not correct format")
    private final String email;

    @NotNull(message = "password is mandatory")
    private final String password;
    private final String address;
    private final String description;
    private final Boolean gender;
    private RoleDto role;

//    private double point = 0.0;
    private Boolean isActive = true;
    private Boolean nonLock = true;
}
