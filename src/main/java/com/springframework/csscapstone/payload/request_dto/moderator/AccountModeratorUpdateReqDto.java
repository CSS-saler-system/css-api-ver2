package com.springframework.csscapstone.payload.request_dto.moderator;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountModeratorUpdateReqDto implements Serializable {
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String description;

    public AccountModeratorUpdateReqDto(String name, String phone, String email, String address, String description) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
    }
}
