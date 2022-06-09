package com.springframework.csscapstone.payload.basic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class CampaignImageBasicDto implements Serializable {
    private final UUID id;
    private final String path;

    @JsonCreator(mode = PROPERTIES)
    public CampaignImageBasicDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("path") String path) {
        this.id = id;
        this.path = path;
    }
}
