package com.springframework.csscapstone.payload.basic;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDto implements Serializable {
    private final String id;
    private final String name;
}
