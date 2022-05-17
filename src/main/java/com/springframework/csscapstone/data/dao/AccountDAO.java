package com.springframework.csscapstone.data.dao;

import com.springframework.csscapstone.payload.basic.AccountDto;

import java.util.List;

public interface AccountDAO {
    List<AccountDto> findDtoCriteria(String name, String phone, String email, String address, String description, boolean status);
}
