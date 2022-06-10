package com.springframework.csscapstone.data.dao;

import com.springframework.csscapstone.payload.response_dto.admin.AccountResDto;

import java.util.List;

public interface AccountDAO {
    List<AccountResDto> findDtoCriteria(String name, String phone, String email, String address, String description, boolean status);
}
