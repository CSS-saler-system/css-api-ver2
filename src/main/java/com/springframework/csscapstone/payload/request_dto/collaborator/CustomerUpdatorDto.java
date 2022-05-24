package com.springframework.csscapstone.payload.request_dto.collaborator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springframework.csscapstone.config.constant.RegexConstant;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CustomerUpdatorDto implements Serializable {
    @NotNull(message = "The id must not be null")
    private final UUID id;

    @NotEmpty(message = "The name must not be empty")
    private final String name;
    @NotEmpty(message = "The phone must not be empty")
    @Pattern(regexp = RegexConstant.REGEX_PHONE)
    private final String phone;
    @NotEmpty(message = "The address must not be empty")
    private final String address;
    @NotNull(message = "The day_of_birth must not be null")
    private final LocalDate dob;
    @NotNull(message = "The account_updater must not be null")
    private final AccountDto accountUpdater;
    @NotEmpty(message = "The address must not be empty")
    private final String description;

    @JsonCreator
    public CustomerUpdatorDto(
            @JsonProperty UUID id,
            @JsonProperty String name,
            @JsonProperty String phone,
            @JsonProperty String address,
            @JsonProperty LocalDate dob,
            @JsonProperty AccountDto accountUpdater,
            @JsonProperty String description) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.accountUpdater = accountUpdater;
        this.description = description;
    }

    @Data
    public static class AccountDto implements Serializable {
        @NotNull(message = "The id must not be null")
        private final UUID id;

        @JsonCreator
        public AccountDto(@JsonProperty UUID id) {
            this.id = id;
        }
    }
}
