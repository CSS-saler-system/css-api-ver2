package com.springframework.csscapstone.payload.request_dto.admin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class AccountCreatorReqDto {
    @NotEmpty(message = "The name must be not empty")
    private final String name;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private final LocalDate dayOfBirth;

    @NotEmpty(message = "The phone must be not empty")
    private final String phone;

    @NotEmpty(message = "The email must be not empty")
    @Email(message = "Email is not correct format")
    private final String email;

    @NotEmpty(message = "The password must be not empty")
    private final String password;

    @NotEmpty(message = "The address must be not empty")
    private final String address;

    @NotEmpty(message = "The description must be not empty")
    private final String description;

    @NotNull(message = "The gender must be not empty")
    private final Boolean gender;

    @NotEmpty(message = "The role must be not empty")
    private String role;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)

    public AccountCreatorReqDto(
            @JsonProperty("name") String name,
            @JsonProperty("dayOfBirth") LocalDate dayOfBirth,
            @JsonProperty("phone") String phone,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("address") String address,
            @JsonProperty("description") String description,
            @JsonProperty("gender") Boolean gender,
            @JsonProperty("role") String role) {
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        this.description = description;
        this.gender = gender;
        this.role = role;
    }
}
