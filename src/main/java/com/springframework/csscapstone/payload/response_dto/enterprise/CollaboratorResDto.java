package com.springframework.csscapstone.payload.response_dto.enterprise;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.springframework.csscapstone.data.status.AccountImageType;
import com.springframework.csscapstone.payload.basic.AccountImageBasicDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

@Data
public class CollaboratorResDto implements Serializable {
    private final UUID id;
    private final String name;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private final LocalDate dob;
    private final String phone;
    private final String address;
    private final String description;
    private final Boolean gender;
    private final Double point;
    private final List<AccountImageBasicDto> imageBasicDto;
    private final Long totalQuantity;

    @JsonCreator(mode = PROPERTIES)
    public CollaboratorResDto(
            @JsonProperty("id") UUID id,
            @JsonProperty("name") String name,
            @JsonProperty("dob") LocalDate dob,
            @JsonProperty("phone") String phone,
            @JsonProperty("address") String address,
            @JsonProperty("description") String description,
            @JsonProperty("gender") Boolean gender,
            @JsonProperty("point") Double point,
            @JsonProperty("avatar") List<AccountImageBasicDto> imageBasicDto,
            @JsonProperty("total_sold") Long totalQuantity) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
        this.description = description;
        this.gender = gender;
        this.point = point;
        this.imageBasicDto = imageBasicDto;
        this.totalQuantity = totalQuantity;
    }
}
