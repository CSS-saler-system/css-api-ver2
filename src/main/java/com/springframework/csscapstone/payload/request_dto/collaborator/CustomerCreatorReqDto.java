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
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CustomerCreatorReqDto implements Serializable {

    @NotEmpty(message = "name must not be empty")
    private final String name;

    @NotEmpty(message = "The phone must not be empty")
    private final String phone;

    @NotEmpty(message = "The address must not be empty")
    private final String address;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private final LocalDate dob;

    private final AccountInnerCustomerCreatorDto accountCreator;

    @NotEmpty(message = "The description must not be empty")
    private final String description;

    @JsonCreator
    public CustomerCreatorReqDto(
            @JsonProperty("name") String name,
            @JsonProperty("phone") String phone,
            @JsonProperty("address") String address,
            @JsonProperty("dob") LocalDate dob,
            @JsonProperty("accountCreator") AccountInnerCustomerCreatorDto accountCreator,
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
    public static class AccountInnerCustomerCreatorDto implements Serializable {
        @NotEmpty(message = "The dto id must not be empty")
        private final UUID id;

        @JsonCreator
        public AccountInnerCustomerCreatorDto(@JsonProperty("id") UUID id) {
            this.id = id;
        }
    }
}
