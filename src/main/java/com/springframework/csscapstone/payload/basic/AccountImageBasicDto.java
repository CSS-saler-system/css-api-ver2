package com.springframework.csscapstone.payload.basic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.status.AccountImageType;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class AccountImageBasicDto implements Serializable {
    private final UUID id;
    private final AccountImageType type;
    private final String path;
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AccountImageBasicDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("account_image_type") AccountImageType type,
            @JsonProperty("path") String path) {
        this.id = id;
        this.type = type;
        this.path = path;
    }
}
