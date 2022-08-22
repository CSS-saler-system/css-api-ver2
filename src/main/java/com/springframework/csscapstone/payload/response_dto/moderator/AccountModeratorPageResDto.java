package com.springframework.csscapstone.payload.response_dto.moderator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.data.domain.AccountImageBasicVer2Dto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class AccountModeratorPageResDto implements Serializable {
    private final UUID id;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final Double point;
    private final Boolean isActive;
    private final List<AccountImageBasicVer2Dto> images;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AccountModeratorPageResDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("address") String address,
            @JsonProperty("point") Double point,
            @JsonProperty("isActive") Boolean isActive,
            @JsonProperty("") List<AccountImageBasicVer2Dto> images) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.point = point;
        this.isActive = isActive;
        this.images = images;
    }
}
