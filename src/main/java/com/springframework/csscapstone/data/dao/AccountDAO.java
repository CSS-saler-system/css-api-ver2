package com.springframework.csscapstone.data.dao;

import com.springframework.csscapstone.payload.basic.AccountBasicDto;

import java.util.List;

public interface AccountDAO {
    List<AccountBasicDto> findDtoCriteria(String name, String phone, String email, String address, String description, boolean status);
}
