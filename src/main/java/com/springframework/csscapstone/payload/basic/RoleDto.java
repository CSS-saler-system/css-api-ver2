package com.springframework.csscapstone.payload.basic;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class RoleDto implements Serializable {
    @NotEmpty(message = "The id is mandatory")
    private final String id;
    @NotEmpty(message = "The name is mandatory")
    private final String name;
}
