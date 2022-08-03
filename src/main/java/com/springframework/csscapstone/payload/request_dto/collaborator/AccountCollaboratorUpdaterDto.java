package com.springframework.csscapstone.payload.request_dto.collaborator;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class AccountCollaboratorUpdaterDto implements Serializable {
    private final String name;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate dob;
    private final String email;
    private final String address;
    private final String description;
    private final Boolean gender;

    @JsonCreator(mode = PROPERTIES)
    public AccountCollaboratorUpdaterDto(
            @JsonProperty("name") String name,
            @JsonProperty("dob") LocalDate dob,
            @JsonProperty("email") String email,
            @JsonProperty("address") String address,
            @JsonProperty("description") String description,
            @JsonProperty("gender") Boolean gender) {
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.address = address;
        this.description = description;
        this.gender = gender;
    }
}
