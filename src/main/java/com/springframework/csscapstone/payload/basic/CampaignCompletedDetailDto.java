package com.springframework.csscapstone.payload.basic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class CampaignCompletedDetailDto implements Serializable {
    private final String collaboratorName;
    private final String phoneNumber;
    private final String prizeName;
    private final Double price;

    @JsonCreator(mode = PROPERTIES)
    public CampaignCompletedDetailDto(
            @JsonProperty("collaboratorName")  String collaboratorName,
            @JsonProperty("phoneNumber") String phoneNumber,
            @JsonProperty("prizeName") String prizeName,
            @JsonProperty("price") Double price) {
        this.collaboratorName = collaboratorName;
        this.phoneNumber = phoneNumber;
        this.prizeName = prizeName;
        this.price = price;
    }
}
