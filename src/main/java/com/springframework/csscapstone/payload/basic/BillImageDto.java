package com.springframework.csscapstone.payload.basic;

import lombok.Data;

import java.io.Serializable;

@Data
public class BillImageDto implements Serializable {
    private final Long id;
    private final String name;
    private final String path;
}
