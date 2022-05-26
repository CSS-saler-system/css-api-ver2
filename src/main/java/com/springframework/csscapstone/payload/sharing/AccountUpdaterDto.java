package com.springframework.csscapstone.payload.sharing;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.springframework.csscapstone.data.domain.AccountImage;
import com.springframework.csscapstone.payload.response_dto.admin.AccountResponseDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class AccountUpdaterDto {
    @NotNull(message = "The id must not be empty")
    private final UUID id;

    @NotEmpty(message = "The phone must not be empty")
    private final String name;

    @NotNull(message = "The id must not be empty")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private final LocalDate dob;

    @NotEmpty(message = "The phone must not be empty")
    private final String phone;

    @NotEmpty(message = "The email must be not empty")
    private final String email;

    @NotEmpty(message = "The address must be not empty")
    private final String address;

    @NotEmpty(message = "The description be not empty")
    private final String description;
    @NotNull(message = "The gender must be not null")
    private final Boolean gender;

    //todo maybe separate following role
    private AccountResponseDto.AccountImageDto identityImage;
    private AccountResponseDto.AccountImageDto avatar;
    private AccountResponseDto.AccountImageDto license;

    public AccountUpdaterDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("dob") LocalDate dob,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("address") String address,
            @JsonProperty("description") String description,
            @JsonProperty("gender") Boolean gender) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.description = description;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "AccountUpdaterDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", gender=" + gender +
                '}';
    }
}
