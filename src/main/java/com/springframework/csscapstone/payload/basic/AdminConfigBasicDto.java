package com.springframework.csscapstone.payload.basic;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminConfigBasicDto implements Serializable {
    private final Long id;
    private final Double feeAdmin;
}
