package com.springframework.csscapstone.services.model_dto.basic;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminConfigDto implements Serializable {
    private final Long id;
    private final Double feeAdmin;
}
