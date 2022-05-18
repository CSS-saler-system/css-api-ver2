package com.springframework.csscapstone.payload.request_dto.customer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CustomerCreatorDto implements Serializable {

    @NotEmpty(message = "name must not be empty")
    private final String name;

    @NotEmpty(message = "The phone must not be empty")
    private final String phone;

    @NotEmpty(message = "The address must not be empty")
    private final String address;

    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "The day of birth must be not null")
    private final LocalDate dob;

    private final AccountDto accountCreator;

    @NotEmpty(message = "The description must not be empty")
    private final String description;

    @JsonCreator
    public CustomerCreatorDto(@JsonProperty("name") String name,
                              @JsonProperty("phone") String phone,
                              @JsonProperty("address") String address,
                              @JsonProperty("dob") LocalDate dob,
                              @JsonProperty("accountCreator") AccountDto accountCreator,
                              @JsonProperty("description") String description) {
        super();
        this.phone = phone;
        this.address = address;
        this.name = name;
        this.dob = dob;
        this.accountCreator = accountCreator;
        this.description = description;
    }

    @Data
    public static class AccountDto implements Serializable {
        @NotEmpty(message = "The dto id must not be empty")
        private final UUID id;
        @JsonCreator
        public AccountDto(@JsonProperty("id") UUID id) {
            this.id = id;
        }
    }
}
