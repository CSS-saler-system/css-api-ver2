package com.springframework.csscapstone.data.repositories;

import com.springframework.csscapstone.data.domain.Account;

import java.util.List;

public interface AccountDao {
    public List<Account> findAllByCriteriaBuilder(String name, String phone,
                                                  String email, String address, String description, boolean status);
}
