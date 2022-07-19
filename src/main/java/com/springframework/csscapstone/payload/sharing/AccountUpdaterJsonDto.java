package com.springframework.csscapstone.payload.sharing;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.springframework.csscapstone.payload.basic.AccountImageBasicDto;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class AccountUpdaterJsonDto {
    @NotNull(message = "The id must not be empty")
    private final UUID id;

    @NotEmpty(message = "The phone must not be empty")
    private final String name;

    private final String phone;

    @NotNull(message = "The id must not be empty")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate dob;

    @NotEmpty(message = "The email must be not empty")
    private final String email;

    @NotEmpty(message = "The address must be not empty")
    private final String address;

    @NotEmpty(message = "The description be not empty")
    private final String description;
    @NotNull(message = "The gender must be not null")
    private final Boolean gender;

    public AccountUpdaterJsonDto(
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
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "AccountUpdaterDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", gender=" + gender +
                '}';
    }
}
