package com.springframework.csscapstone.payload.basic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class RoleDto implements Serializable {
    @NotEmpty(message = "The id is mandatory")
    private final String id;
    @NotEmpty(message = "The name is mandatory")
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RoleDto(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
    }
}
