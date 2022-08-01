package com.springframework.csscapstone.payload.request_dto.collaborator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CustomerUpdaterReqDto implements Serializable {
    @NotNull(message = "The id must not be null")
    private final UUID customerId;

    @NotEmpty(message = "The name must not be empty")
    private final String name;
//    @Pattern(regexp = RegexConstant.REGEX_PHONE)
    @NotEmpty(message = "The phone must not be empty")
    private final String phone;
    @NotEmpty(message = "The address must not be empty")
    private final String address;

    @NotNull(message = "The day_of_birth must not be null")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate dob;
    @NotNull(message = "The account_updater must not be null")
    private final AccountInnerCustomerUpdaterDto accountUpdater;
    @NotEmpty(message = "The address must not be empty")
    private final String description;

    @JsonCreator
    public CustomerUpdaterReqDto(
            @JsonProperty("customerId") UUID customerId,
            @JsonProperty("name") String name,
            @JsonProperty("phone") String phone,
            @JsonProperty("address") String address,
            @JsonProperty("dob") LocalDate dob,
            @JsonProperty("accountUpdater") AccountInnerCustomerUpdaterDto accountUpdater,
            @JsonProperty("description") String description) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.accountUpdater = accountUpdater;
        this.description = description;
    }

    @Data
    public static class AccountInnerCustomerUpdaterDto implements Serializable {
        @NotNull(message = "The id must not be null")
        private final UUID accountId;

        @JsonCreator
        public AccountInnerCustomerUpdaterDto(@JsonProperty("accountId") UUID accountId) {
            this.accountId = accountId;
        }
    }
}
