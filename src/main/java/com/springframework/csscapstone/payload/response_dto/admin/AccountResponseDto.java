package com.springframework.csscapstone.payload.response_dto.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.springframework.csscapstone.payload.basic.AccountImageDto;
import com.springframework.csscapstone.payload.basic.RoleDto;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class AccountResponseDto implements Serializable {
    private final UUID id;
    private final String name;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate dob;
    private final String phone;
    private final String email;
    private final String address;
    private final String description;
    private final Boolean gender;
    private final Double point;

    private List<AccountImageDto> avatar;
    private List<AccountImageDto> licenses;
    private List<AccountImageDto> idCard;
    private final RoleDto role;
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AccountResponseDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("dob") LocalDate dob,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("address") String address,
            @JsonProperty("description") String description,
            @JsonProperty("gender") Boolean gender,
            @JsonProperty("point") Double point,
            @JsonProperty("role") RoleDto role) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
        this.gender = gender;
        this.point = point;
        this.role = role;
    }




}
