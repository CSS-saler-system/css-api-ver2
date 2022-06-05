package com.springframework.csscapstone.payload.response_dto.collaborator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class CustomerResDto implements Serializable {
    private final UUID id;

    private final String name;

    private final String phone;

    private final String address;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate dob;

    private final AccountCreatorDto accountCreator;

    private final AccountUpdaterDto accountUpdater;

    private final String description;

    private final List<OrderDto> orders;

    public CustomerResDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("phone") String phone,
            @JsonProperty("address") String address,
            @JsonProperty("dob") LocalDate dob,
            @JsonProperty("account_creator") AccountCreatorDto accountCreator,
            @JsonProperty("account_updater") AccountUpdaterDto accountUpdater,
            @JsonProperty("description") String description,
            @JsonProperty("orders") List<OrderDto> orders) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.accountCreator = accountCreator;
        this.accountUpdater = accountUpdater;
        this.description = description;
        this.orders = orders;
    }

    @Data
    public static class AccountCreatorDto implements Serializable {
        private final UUID id;
    }

    @Data
    public static class AccountUpdaterDto implements Serializable {
        private final UUID id;
    }

    @Data
    public static class OrderDto implements Serializable {
        private final UUID id;
    }
}
