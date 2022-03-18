package com.springframework.csscapstone.services.model_dto.custom.search_model;

import com.springframework.csscapstone.services.model_dto.basic.AccountDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class AccountPageList extends PageImpl<AccountDto> implements Serializable {

    public AccountPageList(List<AccountDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public AccountPageList(List<AccountDto> content) {
        super(content);
    }
}
