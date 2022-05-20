package com.springframework.csscapstone.payload.response_dto.collaborator;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class CustomerResponseDto implements Serializable {
    private final UUID id;

    private final String name;

    private final String phone;

    private final String address;

    @JsonFormat(timezone = "yyyy-MM-dd")
    private final LocalDate dob;

    private final AccountCreatorDto accountCreator;

    private final AccountUpdaterDto accountUpdater;

    private final String description;

    private final List<OrderDto> orders;

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
